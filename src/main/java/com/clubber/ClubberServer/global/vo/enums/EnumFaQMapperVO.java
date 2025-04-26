package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumFaqMapperType;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperVO;
import lombok.Getter;

@Getter
public class EnumFaQMapperVO extends EnumDefaultMapperVO implements EnumMapperVO {

	private final String answer;

	public EnumFaQMapperVO(EnumFaqMapperType enumFaqMapperType) {
		super(enumFaqMapperType);
		this.answer = enumFaqMapperType.getAnswer();
	}
}
