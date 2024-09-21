package com.clubber.ClubberServer.global.enummapper;

public interface EnumMapperType {
	String getCode();

	String getTitle();

	default String getImageUrl() {
		return null;
	}
}
