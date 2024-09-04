package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Hashtag implements EnumMapperType {

	MUSIC("음악"), GAME("게임"), PICTURE("사진"), PROGRAMMING("개발"), LANGUAGE("언어"),
	SPORTS("운동"), DANCE("댄스"), VOLUNTEER("봉사"), RELIGION("종교"), STUDY("학술"),
	ETC("해당 없음");

	private final String title;

	private final String imageUrl;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getImageUrl() {
		return imageUrl;
	}
}
