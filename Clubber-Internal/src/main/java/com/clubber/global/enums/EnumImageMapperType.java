package com.clubber.global.enums;

import com.clubber.global.vo.enums.EnumImageMapperVO;
import com.clubber.global.vo.enums.EnumMapperVO;

public interface EnumImageMapperType extends EnumMapperType {

	String getImageUrl();

	@Override
	default EnumMapperVO createVO() {
		return new EnumImageMapperVO(this);
	}
}

