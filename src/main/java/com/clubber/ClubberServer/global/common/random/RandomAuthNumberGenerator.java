package com.clubber.ClubberServer.global.common.random;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomAuthNumberGenerator {

	Random random = new Random();

	public Integer getRandomAuthNumber() {
		return 100000 + random.nextInt(900000);
	}
}
