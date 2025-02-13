package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;

public interface EnumMapperType<T extends EnumMapperVO> {

	String getCode();

	String getTitle();

	T createVO();
}
