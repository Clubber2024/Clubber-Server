package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.EnumSet;
import java.util.Set;

import static com.clubber.ClubberServer.domain.review.domain.Keyword.*;

@Getter
@RequiredArgsConstructor
public enum ReviewKeywordCategory implements EnumDefaultMapperType {

    ACTIVITY_STYLE("활동 스타일", EnumSet.of(
            ACTIVE,
            FREE,
            SYSTEMATIC,
            AUTONOMOUS,
            STABLE
    )),
    CULTURE("분위기", EnumSet.of(
            FRIENDLY,
            COMFORTABLE,
            HIERARCHICAL,
            WELCOMING,
            TEAM_ACTIVITY
    )),
    ACTIVITY("활동", EnumSet.of(
            DIVERSE_ACTIVITY,
            PRACTICAL,
            MAJOR_RELATED,
            CAREER_HELPFUL,
            REGULAR_MEETING
    )),
    COST("비용", EnumSet.of(
            LOW_FEE,
            FAIR_FEE,
            NO_FEE,
            FUNDED
    )),
    MANAGE("운영진", EnumSet.of(
            GOOD_MANAGEMENT,
            GOOD_COMMUNICATION,
            TRANSPARENT,
            RESPONSIBLE
    )),
    TIME_AND_ENGAGEMENT("시간/참여도", EnumSet.of(
            LOW_TIME,
            EXAM_FRIENDLY,
            FLEXIBLE_ATTENDANCE,
            HIGH_ATTENDANCE
    )),
    RECRUITMENT("가입/선발", EnumSet.of(
            INTERVIEW,
            REQUIRE_SPEC,
            SELF_INTRO,
            LOW_COMPETITION
    )),
    MEMBER("구성원", EnumSet.of(
            MIXED_GRADES,
            MOSTLY_FRESHMEN,
            SMALL_GROUP,
            LARGE_GROUP
    ));

    private final String title;
    private final Set<Keyword> reviewKeywords;

    @Override
    public String getCode() {
        return name();
    }

    @Override
    public String getTitle() {
        return title;
    }
}
