package com.clubber.ClubberServer.global.image;

import org.springframework.stereotype.Component;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.IMAGE_SERVER;

@Component
public class ImageUtil {

    public static String parseImageKey(String imageUrl) {
        if (imageUrl.startsWith(IMAGE_SERVER)) {
            return imageUrl.substring(IMAGE_SERVER.length());
        }
        return imageUrl;
    }
}
