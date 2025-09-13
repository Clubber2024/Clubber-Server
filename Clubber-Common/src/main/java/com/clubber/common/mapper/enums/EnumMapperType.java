package com.clubber.common.mapper.enums;

import com.clubber.common.vo.enums.EnumMapperVO;

public interface EnumMapperType {

	String getCode();

	String getTitle();

	EnumMapperVO createVO();
}

