package com.clubber.global.enums;

import com.clubber.global.vo.enums.EnumMapperVO;

public interface EnumMapperType {

	String getCode();

	String getTitle();

	EnumMapperVO createVO();
}

