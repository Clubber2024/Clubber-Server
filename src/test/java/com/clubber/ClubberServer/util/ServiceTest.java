package com.clubber.ClubberServer.util;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class ServiceTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@BeforeEach
	void setInitialData(){
		databaseCleaner.insertInitialData();
	}
}
