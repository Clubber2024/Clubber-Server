package com.clubber.ClubberServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ClubberServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubberServerApplication.class, args);
	}

}
