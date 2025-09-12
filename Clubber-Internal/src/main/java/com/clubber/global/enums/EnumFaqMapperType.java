package com.clubber.global.enums;

import com.clubber.global.vo.enums.EnumFaQMapperVO;
import com.clubber.global.vo.enums.EnumMapperVO;

public interface EnumFaqMapperType extends EnumMapperType {

	String getAnswer();

	@Override
	default EnumMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}
}
