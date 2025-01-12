package com.clubber.ClubberServer.global.vo.image;

import static com.clubber.ClubberServer.global.common.ClubberStatic.*;

import com.fasterxml.jackson.annotation.JsonValue;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageVO {

	private String imageUrl;

	@JsonValue
	public String generateImageUrl(){
		return IMAGE_SERVER + imageUrl;
	}

	public ImageVO(String key) {
		this.imageUrl = key;
	}
	public static ImageVO valueOf(String key){
		return new ImageVO(key);
	}

	public static String from(String key){
		return IMAGE_SERVER + key;
	}
}
