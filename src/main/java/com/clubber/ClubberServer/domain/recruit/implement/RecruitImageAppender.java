package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RecruitImageAppender {

    private final RecruitImageRepository recruitImageRepository;

    public void deleteRecruitImages(List<RecruitImage> recruitImages) {
        recruitImages.stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .forEach(RecruitImage::delete);
    }
}
