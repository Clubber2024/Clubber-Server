package com.clubber.ClubberServer.global.image;

import static com.clubber.ClubberServer.global.jwt.JwtStatic.IMAGE_SERVER;

public class ImageUtil {

    public static String parseImageKey(String imageUrl) {
        if (imageUrl.startsWith(IMAGE_SERVER)) {
            return imageUrl.substring(IMAGE_SERVER.length());
        }
        return imageUrl;
    }
}
