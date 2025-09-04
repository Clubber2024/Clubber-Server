package com.clubber.ClubberInternal.global.vo.enums;

import com.clubber.ClubberInternal.global.enums.EnumMapperType;
import lombok.Getter;

@Getter
public class EnumMapperVO {

	private final String code;
	private final String title;

	public EnumMapperVO(EnumMapperType enumMapperType) {
		this.code = enumMapperType.getCode();
		this.title = enumMapperType.getTitle();
	}
}
