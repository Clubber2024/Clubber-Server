package com.clubber.domain.review.dto;

import com.clubber.domain.domains.review.domain.Keyword;
import com.clubber.global.validator.enums.Enum;
import com.clubber.global.validator.unique.Unique;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateClubReviewRequest {

	@Size(max = 100, message = "리뷰 작성은 100자까지 가능합니다")
	@Schema(description = "작성하고자하는 한줄평", example = "활동이 재밌어요")
	private String content;

	@Size(min = 1, message = "1개 이상의 키워드를 선택해주세요")
	@Schema(description = "선택하려는 키워드")
	@Unique
	private List<@Enum(
		target = Keyword.class,
		message = "유효하지 않은 리뷰 키워드입니다"
	) Keyword> keywords = new ArrayList<>();

	private String authImage;
}
