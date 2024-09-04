package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Hashtag implements EnumMapperType {

	MUSIC("음악", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/music.png"),
	GAME("게임", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/game.png"),
	PICTURE("사진", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/photo.png"),
	PROGRAMMING("개발", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/programming.png"),
	LANGUAGE("언어", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/language.png"),
	SPORTS("운동", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/sports.png"),
	DANCE("댄스", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/dance.png"),
	VOLUNTEER("봉사", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/volunteer.png"),
	RELIGION("종교", "https://clubber-bucket.s3.ap-northeast-2.amazonaws.com/hashtag/religion.png"),
	STUDY("학술", null),
	ETC("해당 없음", null);

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
