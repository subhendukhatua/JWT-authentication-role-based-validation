package com.springsecurity.service;

import com.springsecurity.dto.JWTAuthenticationResponse;
import com.springsecurity.dto.RefreshTokenRequest;
import com.springsecurity.dto.SignInRequest;
import com.springsecurity.dto.SignUpRequest;
import com.springsecurity.entities.User;

public interface AuthenticationService {
	User signUp(SignUpRequest signUpRequest);
	
	JWTAuthenticationResponse signin(SignInRequest signInRequest);
	JWTAuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest);

}
