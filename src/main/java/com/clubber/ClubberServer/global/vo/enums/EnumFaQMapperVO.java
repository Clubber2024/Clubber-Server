package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumFaQMapperType;
import lombok.Getter;

@Getter
public class EnumFaQMapperVO extends EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaQMapperType enumFaQMapperType) {
		super(enumFaQMapperType);
		this.answer = enumFaQMapperType.getAnswer();
	}
}
