package com.springsecurity.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JWTService {

	String extractUsername(String token);
	String generateToken(UserDetails userDetails);
	
	boolean isTokenValid(String token, UserDetails userDetails);
	String generateRefreshToken(Map<String, Object>extraClaims, UserDetails userDetails);
}
