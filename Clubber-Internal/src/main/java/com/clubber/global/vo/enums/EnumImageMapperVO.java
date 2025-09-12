package com.clubber.global.vo.enums;

import com.clubber.global.enums.EnumImageMapperType;
import lombok.Getter;

@Getter
public class EnumImageMapperVO extends EnumMapperVO {

	private final String imageUrl;

	public EnumImageMapperVO(EnumImageMapperType enumImageMapperType) {
		super(enumImageMapperType);
		this.imageUrl = enumImageMapperType.getImageUrl();
	}
}
