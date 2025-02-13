package com.clubber.ClubberServer.global.config.enums;


import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.domain.club.domain.Division;
import com.clubber.ClubberServer.domain.club.domain.Hashtag;
import com.clubber.ClubberServer.domain.review.domain.Keyword;
import com.clubber.ClubberServer.global.mapper.enums.EnumImageMapperType;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;
import com.clubber.ClubberServer.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

	@Bean
	public EnumMapper<EnumMapperVO, EnumMapperType<EnumMapperVO>> getEnumMapper() {
		EnumMapper<EnumMapperVO, EnumMapperType<EnumMapperVO>> enumMapper = new EnumMapper<>();
		enumMapper.put("Keyword", Keyword.class);
		enumMapper.put("Division", Division.class);
		enumMapper.put("Department", Department.class);
		enumMapper.put("College", College.class);
		enumMapper.put("ClubType", ClubType.class);
		return enumMapper;
	}

	@Bean
	public EnumMapper<EnumImageMapperVO, EnumImageMapperType> getEnumImageMapper() {
		EnumMapper<EnumImageMapperVO, EnumImageMapperType> enumImageMapper = new EnumMapper<>();
		enumImageMapper.put("Hashtag", Hashtag.class);
		return enumImageMapper;
	}
}
