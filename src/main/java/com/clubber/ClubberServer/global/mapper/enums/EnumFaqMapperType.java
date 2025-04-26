package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumFaQMapperVO;

public interface EnumFaqMapperType extends EnumMapperType {

	String getAnswer();

	@Override
	default EnumMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}
}
