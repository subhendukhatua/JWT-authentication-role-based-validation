package com.springsecurity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springsecurity.dto.JWTAuthenticationResponse;
import com.springsecurity.dto.RefreshTokenRequest;
import com.springsecurity.dto.SignInRequest;
import com.springsecurity.dto.SignUpRequest;
import com.springsecurity.entities.User;
import com.springsecurity.service.AuthenticationService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationService authenticationService;
	
	@PostMapping("/signUp")
	public ResponseEntity<User>signUp(@RequestBody SignUpRequest signUpRequest){
		return ResponseEntity.ok(authenticationService.signUp(signUpRequest));
		
	}
	
	@PostMapping("/signin")
	public ResponseEntity<JWTAuthenticationResponse> signin(@RequestBody SignInRequest signInRequest){
		
		return ResponseEntity.ok(authenticationService.signin(signInRequest));
		
	}
	
	
	@PostMapping("/refresh")
	public ResponseEntity<JWTAuthenticationResponse> refresh(@RequestBody RefreshTokenRequest refreshTokenRequest){
		
		return ResponseEntity.ok(authenticationService.refreshToken(refreshTokenRequest));
		
	}

}
