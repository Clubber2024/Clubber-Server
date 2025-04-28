package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;

public interface EnumMapperType {

	String getCode();

	String getTitle();

	EnumMapperVO createVO();
}

