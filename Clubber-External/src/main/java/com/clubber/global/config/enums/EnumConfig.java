package com.clubber.global.config.enums;


import com.clubber.domain.domains.report.domain.ReportReason;
import com.clubber.domain.domains.report.domain.ReportStatus;
import com.clubber.domain.faq.domain.Faq;
import com.clubber.domain.domains.review.domain.Keyword;
import com.clubber.domain.domains.review.domain.ReviewKeywordCategory;
import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.domain.domains.club.domain.*;
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
		enumMapper.put("ReportReason", ReportReason.class);
		return enumMapper;
	}
}
