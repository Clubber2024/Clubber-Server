package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumFaqMapperType;
import lombok.Getter;

@Getter
public class EnumFaQMapperVO extends EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaqMapperType enumFaqMapperType) {
		super(enumFaqMapperType);
		this.answer = enumFaqMapperType.getAnswer();
	}
}
