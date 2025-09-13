package com.clubber;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan(basePackages = "com.clubber.global.properties")
@SpringBootApplication
public class ClubberServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ClubberServerApplication.class, args);
	}

}
