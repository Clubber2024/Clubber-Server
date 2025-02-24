package com.clubber.ClubberServer.global.config.security;

import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
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

	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public void configure(HttpSecurity builder) {
		builder.addFilterBefore(new JwtTokenFilter(jwtTokenProvider),
			UsernamePasswordAuthenticationFilter.class);
	}
}
