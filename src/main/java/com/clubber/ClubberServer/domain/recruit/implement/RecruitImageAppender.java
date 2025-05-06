package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RecruitImageAppender {

    private final RecruitImageRepository recruitImageRepository;

    public void deleteRecruitImages(List<RecruitImage> recruitImages) {
        recruitImages.stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .forEach(RecruitImage::delete);
    }

    public void appendRecruitImages(List<String> imageKeys, Recruit recruit) {
        AtomicLong order = new AtomicLong(1L);

        imageKeys.stream()
                .map(imageUrl -> {
                    RecruitImage recruitImage = RecruitImage.of(ImageVO.valueOf(imageUrl), recruit, order.getAndIncrement());
                    recruitImageRepository.save(recruitImage);
                    return recruitImage;
                })
                .collect(Collectors.toList());
    }
}
