package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumImageMapperType;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;
import lombok.Getter;

@Getter
public class EnumMapperVO {

	private final String code;
	private final String title;

	public EnumMapperVO(EnumMapperType<? extends EnumMapperVO> enumMapperType) {
		this.code = enumMapperType.getCode();
		this.title = enumMapperType.getTitle();
	}
}
