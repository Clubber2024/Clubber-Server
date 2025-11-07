package com.clubber.domain.review.dto;

import com.clubber.domain.domains.review.domain.ReviewKeywordCategory;
import com.clubber.common.vo.enums.EnumMapperVO;

import java.util.List;

public record ReviewKeywordCategoryResponse(
        ReviewKeywordCategory reviewKeywordCategory,
        List<EnumMapperVO> keywords
) {
}
