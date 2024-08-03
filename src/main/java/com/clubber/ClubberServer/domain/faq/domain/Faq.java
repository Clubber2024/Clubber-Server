package com.clubber.ClubberServer.domain.faq.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Faq implements EnumMapperType {
    QUESTION_1("질문1", "답변1"),
    QUESTION_2("질문2", "답변2");

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getAnswer() {
        return answer;
    }

    private String title;
    private String answer;
}
