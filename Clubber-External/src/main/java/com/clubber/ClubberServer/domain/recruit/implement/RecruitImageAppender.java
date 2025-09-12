package com.clubber.ClubberServer.domain.recruit.implement;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.repository.RecruitImageRepository;
import com.clubber.common.vo.image.ImageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
@RequiredArgsConstructor
public class RecruitImageAppender {

    private final RecruitImageRepository recruitImageRepository;

    public void deleteRecruitImages(List<RecruitImage> recruitImages) {
        recruitImages.stream()
                .filter(recruitImage -> !recruitImage.isDeleted())
                .forEach(RecruitImage::delete);
    }

    public List<RecruitImage> appendRecruitImages(List<String> imageKeys, Recruit recruit) {
        AtomicLong order = new AtomicLong(1L);

        return imageKeys.stream()
                .map(imageUrl -> {
                    ImageVO imageVO = ImageVO.valueOf(imageUrl);
                    RecruitImage recruitImage = RecruitImage.of(imageVO, recruit, order.getAndIncrement());
                    return recruitImageRepository.save(recruitImage);
                })
                .toList();
    }
}
