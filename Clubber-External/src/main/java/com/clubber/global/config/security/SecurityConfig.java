package com.clubber.global.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
                requests.requestMatchers("/api/v1/auths/oauth/**")
                    .permitAll()
                    .requestMatchers("/api/v1/auths/refresh")
                    .permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/v1/clubs/{clubId}/reviews/**")
                    .permitAll()
                    .requestMatchers("/api/v1/images/admin/sign-up/verify")
                    .permitAll()
                    .requestMatchers("/api/v1/images/club/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/v1/clubs/popular")
                    .permitAll()
                    .requestMatchers("/api/v1/clubs/{clubId}")
                    .permitAll()
                    .requestMatchers("/api/v1/clubs")
                    .permitAll()
                    .requestMatchers("/api/v1/notices/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/login", "/api/v1/admins/refresh")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/sign-up")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/auths/me/update-email/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/v1/admins/auths/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/username/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/password/reset")
                    .permitAll()
                    .requestMatchers("/api/v1/keywords")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/**")
                    .hasRole("ADMIN")
                    .requestMatchers("/api/v1/clubs/{clubId}/recruit")
                    .permitAll()
                    .requestMatchers("/api/v1/recruits")
                    .permitAll()
                    .requestMatchers("/api/v1/recruits/{recruitId}")
                    .permitAll()
                    .requestMatchers("/api/v1/faqs")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/verify")
                    .permitAll()
                    .requestMatchers("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
                        "/v3/api-docs")
                    .permitAll()
                    .requestMatchers("/exceptions/**")
                    .permitAll()
                    .requestMatchers("/actuator/**")
                    .permitAll()
                    .requestMatchers("/api/v1/clubs/popular/temp")
                    .permitAll()
                    .requestMatchers("/api/v1/example/**")
                    .permitAll()
                    .requestMatchers("/api/v1/perspective/**")
                    .permitAll()
                    .requestMatchers("/api/v1/calendars/**")
                    .permitAll()
                    .requestMatchers("/api/v1/admins/calendars/**")
                    .hasRole("ADMIN")
                    .anyRequest()
                    .hasRole("USER"));
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring()
            .requestMatchers("/api/v1/auths/oauth/")
            .requestMatchers("/api/v1/auths/refresh")
            .requestMatchers("/api/v1/clubs")
            .requestMatchers("/api/v1/clubs/{clubId}")
            .requestMatchers("/api/v1/clubs/category/**")
            .requestMatchers("/api/v1/clubs/summary")
            .requestMatchers("/api/v1/clubs/popular")
            .requestMatchers(HttpMethod.GET, "/api/v1/clubs/{clubId}/reviews/**")
            .requestMatchers("/api/v1/notices/**")
            .requestMatchers("/api/v1/admins/login")
            .requestMatchers("/api/v1/admins/sign-up")
            .requestMatchers("/api/v1/admins/auths/sign-up/**")
            .requestMatchers("/api/v1/admins/auths/find-username/**")
            .requestMatchers("/api/v1/admins/auths/reset-password/**")
            .requestMatchers("/api/v1/admins/username-duplicate")
            .requestMatchers("/api/v1/admins/refresh")
            .requestMatchers("/api/v1/admins/password/reset")
            .requestMatchers("/api/v1/clubs/{clubId}/recruit")
            .requestMatchers("/api/v1/recruits/**")
            .requestMatchers("/api/v1/keywords")
            .requestMatchers("/api/v1/faqs")
            .requestMatchers("/api/v1/example/**")
            .requestMatchers("/api/v1/admins/verify")
            .requestMatchers("/api/v1/images/admin/sign-up/verify")
            .requestMatchers("/api/v1/calendar/**")
            .requestMatchers("/swagger-resources/**", "/swagger-ui/**", "/v3/api-docs/**",
                "/v3/api-docs")
            .requestMatchers("/api/v1/admins/mail-auth")
            .requestMatchers("/actuator/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
