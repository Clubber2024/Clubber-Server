package com.clubber.global.config.security;

import com.clubber.global.helper.SpringEnvironmentHelper;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class CorsConfig implements WebMvcConfigurer {

	private final SpringEnvironmentHelper springEnvironmentHelper;

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		List<String> allowedOriginPatterns = new ArrayList<>();

		//운영 서버
		allowedOriginPatterns.add("https://ssuclubber.com");

		//개발 서버 : localhost 포함
		if(!springEnvironmentHelper.isProdProfile()){
			allowedOriginPatterns.add("http://localhost:3000");
			allowedOriginPatterns.add("http://localhost:3001");
			allowedOriginPatterns.add("https://dev.ssuclubber.com");
		}

		String[] patterns = allowedOriginPatterns.toArray(String[]::new);

		registry.addMapping("/**")
			.allowedMethods("*")
			.allowedOriginPatterns(patterns)
//                .exposedHeaders("Set-Cookie")
			.allowCredentials(true);
	}
}