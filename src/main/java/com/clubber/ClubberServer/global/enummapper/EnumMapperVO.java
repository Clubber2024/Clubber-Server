package com.clubber.ClubberServer.global.enummapper;

import lombok.Getter;

@Getter
public class EnumMapperVO {
	private final String code;
	private final String title;
	private final String imageUrl;

	public EnumMapperVO(EnumMapperType enumMapperType) {
		this.code = enumMapperType.getCode();
		this.title = enumMapperType.getTitle();
		this.imageUrl = enumMapperType.getImageUrl();
	}
}
