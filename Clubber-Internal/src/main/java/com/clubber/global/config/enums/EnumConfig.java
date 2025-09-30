package com.clubber.global.config.enums;


import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.domain.domains.report.domain.ReportStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

	@Bean
	public EnumMapper getEnumMapper() {
		final EnumMapper enumMapper = new EnumMapper();
		enumMapper.put("ReportStatus", ReportStatus.class);
		return enumMapper;
	}
}
