package com.clubber.ClubberServer.global.config.enums;


import com.clubber.ClubberServer.domain.club.domain.*;
import com.clubber.ClubberServer.domain.faq.domain.Faq;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.domain.review.domain.ReviewKeywordCategory;
import com.clubber.common.mapper.enums.EnumMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

	@Bean
	public EnumMapper getEnumMapper() {
		final EnumMapper enumMapper = new EnumMapper();
		enumMapper.put("Keyword", Keyword.class);
		enumMapper.put("Division", Division.class);
		enumMapper.put("Department", Department.class);
		enumMapper.put("College", College.class);
		enumMapper.put("ClubType", ClubType.class);
		enumMapper.put("Hashtag", Hashtag.class);
		enumMapper.put("FaQ", Faq.class);
		enumMapper.put("ReviewKeywordCategory", ReviewKeywordCategory.class);
		return enumMapper;
	}
}
