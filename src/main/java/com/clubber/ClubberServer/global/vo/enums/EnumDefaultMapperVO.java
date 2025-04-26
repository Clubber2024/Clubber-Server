package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperVO;
import lombok.Getter;

@Getter
public class EnumDefaultMapperVO implements EnumMapperVO {

	private final String code;
	private final String title;

	public EnumDefaultMapperVO(EnumMapperType enumMapperType) {
		this.code = enumMapperType.getCode();
		this.title = enumMapperType.getTitle();
	}
}
