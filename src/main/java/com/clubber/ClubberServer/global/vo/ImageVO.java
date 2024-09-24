package com.clubber.ClubberServer.global.vo;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.*;

import com.fasterxml.jackson.annotation.JsonValue;

public class ImageVO {

	private String imageKey;

	@JsonValue
	public String generateImageUrl(){
		return IMAGE_SERVER + imageKey;
	}
}
