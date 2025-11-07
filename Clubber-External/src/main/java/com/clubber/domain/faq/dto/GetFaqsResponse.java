package com.clubber.domain.faq.dto;

import com.clubber.domain.faq.domain.Faq;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFaqsResponse {

    @Schema(description = "faq code", example = "QUESTION_1")
    private final String code;

    @Schema(description = "자주하는 질문 내용", example = "질문1")
    private final String question;

    @Schema(description = "자주하는 질문에 해당하는 답변", example = "답변1")
    private final String answer;

    public static GetFaqsResponse from(Faq faq){
        return GetFaqsResponse.builder()
                .code(faq.getCode())
                .question(faq.getTitle())
                .answer(faq.getAnswer()).build();
    }
}
