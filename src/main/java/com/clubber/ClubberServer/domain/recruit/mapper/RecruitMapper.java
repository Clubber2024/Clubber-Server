package com.clubber.ClubberServer.domain.recruit.mapper;

import com.clubber.ClubberServer.domain.recruit.domain.Recruit;
import com.clubber.ClubberServer.domain.recruit.domain.RecruitImage;
import com.clubber.ClubberServer.domain.recruit.dto.*;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetOneRecruitMainPageResponse;
import com.clubber.ClubberServer.domain.recruit.dto.mainPage.GetRecruitsMainPageResponse;
import com.clubber.ClubberServer.global.common.page.PageResponse;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecruitMapper {

    public PageResponse<GetOneRecruitInListResponse> getRecruitsPageResponse(Page<Recruit> recruits){
        Page<GetOneRecruitInListResponse> recruitResponses = recruits.map(recruit -> {
        		String content = recruit.getContent().substring(0, Math.min(recruit.getContent().length(), 60));
				ImageVO imageUrl = recruit.getRecruitImages().stream()
						.filter(
        					recruitImage -> !recruitImage.isDeleted() && recruitImage.getOrderNum() == 1)
        				.map(RecruitImage::getImageUrl)
        				.findFirst()
        				.orElse(null);
        		return GetOneRecruitInListResponse.of(recruit, content, imageUrl);
        });
        return PageResponse.of(recruitResponses);
    }

	public List<ImageVO> getDeletedRecruitImages(Recruit recruit){
		return getRecruitImages(recruit);
	}

	public GetOneRecruitWithClubResponse getRecruitsByRecruitId(Recruit recruit){
		List<ImageVO> imageUrls =  getRecruitImages(recruit);
		return GetOneRecruitWithClubResponse.of(recruit, recruit.getClub(), imageUrls);
	}

	public GetOneRecruitResponse getOneAdminRecruitsById(Recruit recruit){
		List<ImageVO> imageUrls =  getRecruitImages(recruit);
		return GetOneRecruitResponse.of(recruit, imageUrls);
	}

	public PostRecruitResponse getRecruitWithImageUrls(Recruit newRecruit,List<RecruitImage> savedImages){
		List<ImageVO> imageUrls = savedImages.stream()
				.sorted(Comparator.comparing(RecruitImage::getOrderNum))
				.map(RecruitImage::getImageUrl)
				.collect(Collectors.toList());

		return PostRecruitResponse.of(newRecruit, imageUrls);
	}

	public List<ImageVO> getRecruitImages(Recruit recruit){
		return recruit.getRecruitImages().stream()
				.filter(recruitImage -> !recruitImage.isDeleted())
				.sorted(Comparator.comparing(RecruitImage::getOrderNum))
				.map(RecruitImage::getImageUrl)
				.collect(Collectors.toList());

	}

	public GetRecruitsMainPageResponse getRecruitsMainPage(List<Recruit> recruits ){
		List<GetOneRecruitMainPageResponse> recruitsDto = recruits.stream()
				.map(GetOneRecruitMainPageResponse::from)
				.collect(Collectors.toList());

		return GetRecruitsMainPageResponse.from(recruitsDto);
	}

}
