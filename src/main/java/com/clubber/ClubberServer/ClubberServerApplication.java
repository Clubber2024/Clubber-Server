package com.clubber.ClubberServer;

import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.properties.KakaoProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients
@SpringBootApplication
public class ClubberServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubberServerApplication.class, args);
	}

}
