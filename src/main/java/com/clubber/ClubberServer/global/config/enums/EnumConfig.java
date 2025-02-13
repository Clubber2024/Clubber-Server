package com.clubber.ClubberServer.global.config.enums;


import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.faq.domain.Faq;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumFaqMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumImageMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

	@Bean
	public EnumDefaultMapper getEnumMapper() {
		EnumDefaultMapper enumMapper = new EnumDefaultMapper();
		enumMapper.put("Keyword", Keyword.class);
		enumMapper.put("Division", Division.class);
		enumMapper.put("Department", Department.class);
		enumMapper.put("College", College.class);
		enumMapper.put("ClubType", ClubType.class);
		return enumMapper;
	}

	@Bean
	public EnumImageMapper getEnumImageMapper() {
		EnumImageMapper enumImageMapper = new EnumImageMapper();
		enumImageMapper.put("Hashtag", Hashtag.class);
		return enumImageMapper;
	}

	@Bean
	public EnumFaqMapper getEnumFaqMapper() {
		EnumFaqMapper enumFaqMapper = new EnumFaqMapper();
		enumFaqMapper.put("FaQ", Faq.class);
		return enumFaqMapper;
	}
}
