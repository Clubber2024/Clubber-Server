package com.clubber.common.vo.enums;

import com.clubber.common.mapper.enums.EnumFaqMapperType;
import lombok.Getter;

@Getter
public class EnumFaQMapperVO extends EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaqMapperType enumFaqMapperType) {
		super(enumFaqMapperType);
		this.answer = enumFaqMapperType.getAnswer();
	}
}
