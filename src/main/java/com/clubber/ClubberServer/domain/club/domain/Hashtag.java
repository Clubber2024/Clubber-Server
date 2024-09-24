package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;
import com.clubber.ClubberServer.global.vo.ImageVO;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Hashtag implements EnumMapperType {

	MUSIC("음악", "music.png"),
	GAME("게임", "game.png"),
	PICTURE("사진", "photo.png"),
	PROGRAMMING("개발",  "programming.png"),
	LANGUAGE("언어",  "language.png"),
	SPORTS("운동",  "sports.png"),
	DANCE("댄스",  "dance.png"),
	VOLUNTEER("봉사", "volunteer.png"),
	RELIGION("종교", "religion.png"),
	STUDY("학술", null),
	ETC("해당 없음", null);

	private static final String HASHTAG_IMAGE_KEY = "hashtag/";

	private final String title;

	private final String imageKey;

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
		return ImageVO.from(HASHTAG_IMAGE_KEY + imageKey);
	}
}
