package com.clubber.ClubberServer.global.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

	private final FilterConfig filterConfig;

	private final CustomAuthenticationEntryPoint entryPoint;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.with(filterConfig, Customizer.withDefaults())
			.exceptionHandling((exceptionConfig) ->
				exceptionConfig.authenticationEntryPoint(entryPoint))
			.authorizeHttpRequests((requests) ->
				requests.requestMatchers("/v1/auths/oauth/**")
					.permitAll()
					.requestMatchers("/v1/auths/refresh")
					.permitAll()
					.requestMatchers(HttpMethod.GET, "/v1/clubs/{clubId}/reviews/**")
					.permitAll()
					.requestMatchers("/v1/images/club/**")
					.hasRole("ADMIN")
					//.requestMatchers(PathRequest.toH2Console()).permitAll()
					.requestMatchers("/v1/clubs/popular")
					.permitAll()
					.requestMatchers("/v1/clubs/{clubId}")
					.permitAll()
					.requestMatchers("/v1/clubs")
					.permitAll()
					.requestMatchers("/v1/notices/**")
					.permitAll()
					.requestMatchers("/v1/admins/login", "/v1/admins/refresh")
					.permitAll()
					.requestMatchers("/v1/keywords")
					.permitAll()
					.requestMatchers("/v1/admins/**")
					.hasRole("ADMIN")
					.requestMatchers("/v1/clubs/{clubId}/recruit")
					.permitAll()
					.requestMatchers("/v1/recruits")
					.permitAll()
					.requestMatchers("/v1/recruits/{recruitId}")
					.permitAll()
					.requestMatchers("/v1/faqs")
					.permitAll()
					.requestMatchers("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**", "/v3/api-docs")
					.permitAll()
					.requestMatchers("/exceptions/**")
					.permitAll()
					.anyRequest()
					.hasRole("USER"));
		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
