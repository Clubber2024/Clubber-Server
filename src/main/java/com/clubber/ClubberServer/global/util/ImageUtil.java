package com.clubber.ClubberServer.global.util;

import static com.clubber.ClubberServer.global.common.consts.ClubberStatic.IMAGE_SERVER;

public class ImageUtil {

    public static String parseImageKey(String imageUrl) {
        if (imageUrl.startsWith(IMAGE_SERVER)) {
            return imageUrl.substring(IMAGE_SERVER.length());
        }
        return imageUrl;
    }
}
