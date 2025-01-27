package com.clubber.ClubberServer.global.mapper.enums;

public interface EnumMapperType {

	String getCode();

	String getTitle();

	default String getImageUrl() {
		return null;
	}
}
