package com.clubber.ClubberServer.global.image;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.IMAGE_SERVER;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ImageUtilTest {
    private final String exampleKey = "imagekey";
    @Mock
    private ImageUtil imageUtil;

    @DisplayName("이미지 url이 이미지 서버로 시작된다면 이미지키가 파싱된다.")
    @Test
    void parseImageKeyTest() {
        assertThat(imageUtil.parseImageKey(IMAGE_SERVER + exampleKey)).isEqualTo(exampleKey);
    }

    @DisplayName("이미지 url이 이미지 서버로 시작되지 않는다면 기존 이미지 url을 반환한다.")
    @Test
    void parseImageKeyNotStartWithImageServer() {
        assertThat(imageUtil.parseImageKey(exampleKey)).isEqualTo(exampleKey);
    }
}
