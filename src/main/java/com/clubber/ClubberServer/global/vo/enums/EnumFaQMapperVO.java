package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumFaqMapperType;

public class EnumFaQMapperVO extends EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaqMapperType enumFaQMapperType) {
		super(enumFaQMapperType);
		this.answer = enumFaQMapperType.getAnswer();
	}
}
