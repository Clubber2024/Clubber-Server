package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumFaQMapperVO;

public interface EnumFaqMapperType extends EnumMapperType<EnumFaQMapperVO> {

	String getAnswer();

	@Override
	default EnumFaQMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}
}
