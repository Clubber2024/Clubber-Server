package com.clubber.common.mapper.enums;

import com.clubber.common.vo.enums.EnumFaQMapperVO;
import com.clubber.common.vo.enums.EnumMapperVO;

public interface EnumFaqMapperType extends EnumMapperType {

	String getAnswer();

	@Override
	default EnumMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}
}
