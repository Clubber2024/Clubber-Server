package com.clubber.ClubberServer.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FilterConfig extends
	SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenFilter jwtTokenFilter;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final AccessDeniedFilter accessDeniedFilter;

	@Override
	public void configure(HttpSecurity builder) {
		builder.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
		builder.addFilterBefore(jwtExceptionFilter, JwtTokenFilter.class);
		//builder.addFilterBefore(accessDeniedFilter, FilterSecurityInterceptor.class);
	}
}
