package com.springsecurity.config;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.springsecurity.service.UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	private final UserService userService;
	
	 @Bean
	  SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		 httpSecurity.csrf(AbstractHttpConfigurer::disable)
		 .authorizeHttpRequests(request-> request.requestMatchers("/auth/**")
				 .permitAll()
				 .requestMatchers("/admin").hasAnyAuthority(com.springsecurity.entities.Role.ADMIN.name())
				 .requestMatchers("/user").hasAnyAuthority(com.springsecurity.entities.Role.USER.name())
				 .anyRequest().authenticated())
		 .sessionManagement(manager->manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		 .authenticationProvider(authenticationProvider()).addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
		 
		 return httpSecurity.build(); 
	 }
	 
	 
	 @Bean
	  public AuthenticationProvider authenticationProvider() {
		 DaoAuthenticationProvider authProvider=new DaoAuthenticationProvider();
		 authProvider.setUserDetailsService(userService.userDetailsService());
		 authProvider.setPasswordEncoder(passwordEncoder());
		 return authProvider;
		 
		 
		 
	 }
	 
	 @Bean
	  public PasswordEncoder passwordEncoder() {
		 return new BCryptPasswordEncoder();
	 }
	 
	 @Bean
	  public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		 return config.getAuthenticationManager();
	 }
	 
	 

}
