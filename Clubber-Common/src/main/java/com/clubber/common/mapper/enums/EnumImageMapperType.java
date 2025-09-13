package com.clubber.common.mapper.enums;

import com.clubber.common.vo.enums.EnumImageMapperVO;
import com.clubber.common.vo.enums.EnumMapperVO;

public interface EnumImageMapperType extends EnumMapperType {

	String getImageUrl();

	@Override
	default EnumMapperVO createVO() {
		return new EnumImageMapperVO(this);
	}
}

