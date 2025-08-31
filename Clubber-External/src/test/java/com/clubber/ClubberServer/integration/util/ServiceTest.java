package com.clubber.ClubberServer.integration.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@ActiveProfiles("test")
public class ServiceTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	void setInitialData() {
		databaseCleaner.insertInitialData();
	}
}
