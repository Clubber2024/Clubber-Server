package com.clubber.ClubberInternal.global.vo.image;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.clubber.ClubberInternal.global.consts.ClubberStatic.IMAGE_SERVER;


@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageVO {

	private String imageUrl;

	public ImageVO(String key) {
		this.imageUrl = key;
	}

	public static ImageVO valueOf(String key) {
		return new ImageVO(key);
	}

	public static String from(String key) {
		return IMAGE_SERVER + key;
	}

	@JsonValue
	public String generateImageUrl() {
		return IMAGE_SERVER + imageUrl;
	}
}
