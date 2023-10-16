package com.springsecurity.service.impl;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.springsecurity.dto.JWTAuthenticationResponse;
import com.springsecurity.dto.RefreshTokenRequest;
import com.springsecurity.dto.SignInRequest;
import com.springsecurity.dto.SignUpRequest;
import com.springsecurity.entities.Role;
import com.springsecurity.entities.User;
import com.springsecurity.repository.UserRepository;
import com.springsecurity.service.AuthenticationService;
import com.springsecurity.service.JWTService;

import lombok.RequiredArgsConstructor;
import lombok.experimental.var;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	private final AuthenticationManager authenticationManager;
	
	private final JWTService jwtService;
	
	
	public User signUp(SignUpRequest signUpRequest) {
		User user = new User();
		user.setEmail(signUpRequest.getEmail());
		user.setFirstname(signUpRequest.getFirstname());
		user.setLastname(signUpRequest.getLastname());
		user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
		user.setRole(Role.USER);
		
		User newUser = userRepository.save(user);
		return newUser;
		
		
	}
	
	public JWTAuthenticationResponse signin(SignInRequest signInRequest) {
		authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.getEmail(),
				signInRequest.getPassword()));
		var user = userRepository.findByEmail(signInRequest.getEmail())
				.orElseThrow(()->new IllegalArgumentException("Invalid email or passworrd"));
		var jwt=jwtService.generateToken(user);
		var refreshToken = jwtService.generateRefreshToken(new HashMap<>(),user);
		
		JWTAuthenticationResponse jwtAuthenticationResponse=new JWTAuthenticationResponse();
		jwtAuthenticationResponse.setToken(jwt);
		jwtAuthenticationResponse.setRefreshToken(refreshToken);
		
		return jwtAuthenticationResponse;	
		
	}
	
	public JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
		String userEmail=jwtService.extractUsername(refreshTokenRequest.getToken());
		User user=userRepository.findByEmail(userEmail).orElseThrow();
		if(jwtService.isTokenValid(refreshTokenRequest.getToken(), user)) {
			var jwt = jwtService.generateToken(user);
			
			JWTAuthenticationResponse jwtAuthenticationResponse=new JWTAuthenticationResponse();
			jwtAuthenticationResponse.setToken(jwt);
			jwtAuthenticationResponse.setRefreshToken(refreshTokenRequest.getToken());
			
			return jwtAuthenticationResponse;
			
		}
		return null;
	}

}
