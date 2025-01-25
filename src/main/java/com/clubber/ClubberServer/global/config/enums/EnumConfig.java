package com.clubber.ClubberServer.global.config.enums;


import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

	@Bean
	public EnumMapper getEnumMapper() {
		EnumMapper enumMapper = new EnumMapper();
		enumMapper.put("Keyword", Keyword.class);
		enumMapper.put("Hashtag", Hashtag.class);
		enumMapper.put("Division", Division.class);
		enumMapper.put("Department", Department.class);
		enumMapper.put("College", College.class);
		enumMapper.put("ClubType", ClubType.class);
		return enumMapper;
	}
}
