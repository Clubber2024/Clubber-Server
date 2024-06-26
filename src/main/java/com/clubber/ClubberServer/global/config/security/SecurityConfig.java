package com.clubber.ClubberServer.global.config.security;

import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

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
                .with(filterConfig,Customizer.withDefaults())
                .exceptionHandling((exceptionConfig) ->
                        exceptionConfig.authenticationEntryPoint(entryPoint))
                .authorizeHttpRequests((requests) ->
                        requests.requestMatchers("/v1/auths/oauth/**").permitAll()
                                .requestMatchers("/v1/auths/refresh").permitAll()
                                .requestMatchers(HttpMethod.GET, "/v1/clubs/{clubId}/reviews/**").permitAll()
                                //.requestMatchers(PathRequest.toH2Console()).permitAll()
                                .requestMatchers("/v1/clubs/**").permitAll()
                                .requestMatchers("/v1/notices").permitAll()
                                .requestMatchers("/swagger-resources/**", "/swagger-ui/**",  "/v3/api-docs/**","/v3/api-docs" ).permitAll()
                                .anyRequest().hasRole("USER"));
        return http.build();
    }
}
