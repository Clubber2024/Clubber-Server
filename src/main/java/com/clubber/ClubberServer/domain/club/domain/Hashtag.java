package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Hashtag implements EnumMapperType {

	MUSIC("밴드"), GAME("게임"), PICTURE("사진"), PROGRAMMING("개발"), CURRENT_AFFAIR("시사"),
	LANGUAGE("언어"), SPORTS("운동"), DANCE("댄스"), VOLUNTEER("봉사"), RELIGION("종교"), STUDY("학술");

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
