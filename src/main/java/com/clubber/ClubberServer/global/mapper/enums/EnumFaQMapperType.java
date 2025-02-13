package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumFaQMapperVO;

public interface EnumFaQMapperType extends EnumMapperType<EnumFaQMapperVO> {

	@Override
	default EnumFaQMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}

	String getAnswer();
}
