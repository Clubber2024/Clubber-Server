package com.clubber.ClubberServer.global.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.IMAGE_SERVER;
import static org.assertj.core.api.Assertions.*;
public class ImageUtilTest {
    private final String exampleKey = "imagekey";

    @DisplayName("이미지 url이 이미지 서버로 시작된다면 이미지키가 파싱된다.")
    @Test
    void parseImageKeyTest() {
        assertThat(ImageUtil.parseImageKey(IMAGE_SERVER + exampleKey)).isEqualTo(exampleKey);
    }

    @DisplayName("이미지 url이 이미지 서버로 시작되지 않는다면 기존 이미지 url을 반환한다.")
    @Test
    void parseImageKeyNotStartWithImageServer() {
        assertThat(ImageUtil.parseImageKey(exampleKey)).isEqualTo(exampleKey);
    }
}
