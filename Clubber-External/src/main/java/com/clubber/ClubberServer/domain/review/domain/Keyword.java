package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapperType;
import com.fasterxml.jackson.annotation.JsonCreator;

import java.util.Arrays;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Keyword implements EnumDefaultMapperType {
    // 활동 스타일
    ACTIVE("🔥 활동이 활발해요"),
    FREE("🔥 자유로워요"),
    SYSTEMATIC("🔥 체계적으로 운영돼요"),
    AUTONOMOUS("🔥 자율성이 높아요"),
    STABLE("🔥 운영이 탄탄해요"),

    // 분위기
    FRIENDLY("👥 친목활동이 많아요"),
    COMFORTABLE("🔥 편안한 분위기에요"),
    HIERARCHICAL("🔥 선후배 관계가 수평적이에요"),
    WELCOMING("🔥 처음 와도 잘 적응 할 수 있어요"),
    TEAM_ACTIVITY("🔥 조 활동이 있어요"),

    // 활동
    DIVERSE_ACTIVITY("🔥 활동이 다양해요"),
    PRACTICAL("🔥 실무/프로젝트 중심이에요"),
    MAJOR_RELATED("🔥 전공과 연계돼요"),
    CAREER_HELPFUL("🔥 대외활동/커리어에 도움이 됐어요"),
    REGULAR_MEETING("🔥 정기 모임이 있어요"),

    // 비용
    LOW_FEE("💵 회비가 저렴해요"),
    FAIR_FEE("💵 회비가 적당해요"),
    NO_FEE("💵 회비가 아깝지 않아요"),
    FUNDED("💵 활동비가 충분히 지원돼요"),

    // 운영진
    GOOD_MANAGEMENT("🔥 운영진이 일을 잘해요"),
    GOOD_COMMUNICATION("🔥 소통이 원활해요"),
    TRANSPARENT("🔥 투명하게 운영돼요"),
    RESPONSIBLE("🔥 회장이 책임감이 있어요"),

    // 시간/참여도
    LOW_TIME("🔥 활동 시간이 적어요"),
    EXAM_FRIENDLY("🔥 시험 기간에 배려해줘요"),
    FLEXIBLE_ATTENDANCE("🔥 참여가 자유로워요"),
    HIGH_ATTENDANCE("🔥 참여 비중이 높아요"),

    // 가입/선발
    INTERVIEW("🔥 면접이 있어요"),
    REQUIRE_SPEC("🔥 스펙이 필요해요"),
    SELF_INTRO("🔥 자기소개서를 제출했어요"),
    LOW_COMPETITION("🔥 진입장벽이 낮아요"),

    // 구성원
    MIXED_GRADES("🔥 다양한 학년이 섞여 있어요"),
    MOSTLY_FRESHMEN("🔥 대부분 1학년이에요"),
    SMALL_GROUP("🔥 소규모 동아리에요"),
    LARGE_GROUP("🔥 대규모 동아리에요");

    private final String title;

    @JsonCreator
    public static Keyword from(String req) {
        return Arrays.stream(Keyword.values())
                .filter(keyword -> keyword.getCode().equals(req))
                .findAny()
                .orElse(null);
    }

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
