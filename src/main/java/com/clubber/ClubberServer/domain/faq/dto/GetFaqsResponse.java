package com.clubber.ClubberServer.domain.faq.dto;

import com.clubber.ClubberServer.domain.faq.domain.Faq;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class GetFaqsResponse {
    private final String code;

    private final String question;

    private final String answer;

    public static GetFaqsResponse from(Faq faq){
        return GetFaqsResponse.builder()
                .code(faq.getCode())
                .question(faq.getTitle())
                .answer(faq.getAnswer()).build();
    }
}
