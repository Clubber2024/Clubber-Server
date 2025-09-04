package com.clubber.ClubberServer.domain.club.implement;

import com.clubber.ClubberServer.domain.club.domain.ClubInfo;
import org.springframework.stereotype.Component;

@Component
public class ClubAppender {

    public void increaseClubTotalView(ClubInfo clubInfo) {
        clubInfo.increaseTotalView();
    }
}
