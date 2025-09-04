package com.clubber.ClubberInternal.global.enums;

import com.clubber.ClubberInternal.global.vo.enums.EnumMapperVO;

public interface EnumMapperType {

	String getCode();

	String getTitle();

	EnumMapperVO createVO();
}

