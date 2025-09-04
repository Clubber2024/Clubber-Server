package com.clubber.ClubberInternal.global.vo.enums;

import com.clubber.ClubberInternal.global.enums.EnumFaqMapperType;
import lombok.Getter;

@Getter
public class EnumFaQMapperVO extends EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaqMapperType enumFaqMapperType) {
		super(enumFaqMapperType);
		this.answer = enumFaqMapperType.getAnswer();
	}
}
