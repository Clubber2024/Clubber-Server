package com.clubber.ClubberServer.global.vo;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.*;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageVO {

	private String imageKey;

	@JsonValue
	public String generateImageUrl(){
		return IMAGE_SERVER + imageKey;
	}

	public ImageVO(String key) {
		this.imageKey = key;
	}
	public static ImageVO valueOf(String key){
		return new ImageVO(key);
	}
}
