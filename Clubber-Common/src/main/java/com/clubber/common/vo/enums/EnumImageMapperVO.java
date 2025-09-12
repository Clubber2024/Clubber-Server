package com.clubber.common.vo.enums;

import com.clubber.common.mapper.enums.EnumImageMapperType;
import lombok.Getter;

@Getter
public class EnumImageMapperVO extends EnumMapperVO {

	private final String imageUrl;

	public EnumImageMapperVO(EnumImageMapperType enumImageMapperType) {
		super(enumImageMapperType);
		this.imageUrl = enumImageMapperType.getImageUrl();
	}
}
