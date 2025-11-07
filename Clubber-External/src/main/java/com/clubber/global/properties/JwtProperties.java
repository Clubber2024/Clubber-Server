package com.clubber.global.properties;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@RequiredArgsConstructor
@ConfigurationProperties("jwt")
public class JwtProperties {

	private final String secretKey;
	private final Long accessExp;
	private final Long refreshExp;
}
