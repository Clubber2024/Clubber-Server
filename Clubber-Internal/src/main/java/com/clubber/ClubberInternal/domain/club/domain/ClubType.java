package com.clubber.ClubberInternal.domain.club.domain;

import com.clubber.ClubberInternal.global.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ClubType implements EnumDefaultMapperType {
	CENTER("중앙동아리"),
	SMALL("소모임"),
	OFFICIAL("공식단체"),
	GENERAL("일반동아리"),
	ETC("기타");

	private final String title;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return title;
	}
}
