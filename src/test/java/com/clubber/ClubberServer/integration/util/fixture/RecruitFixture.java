package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitRequest;
import java.util.List;

public class RecruitFixture {

    // 모집글 작성 테스트 데이터
    public static final PostRecruitRequest VALID_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", "content",
        List.of("imagekey1", "imagekey2"));

    // 모집글 title 유효성 검사
    public static final PostRecruitRequest NULL_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        null, "content",
        List.of("imagekey1", "imagekey2"));

    public static final PostRecruitRequest EMPTY_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "", "content",
        List.of("imagekey1", "imagekey2"));

    public static final PostRecruitRequest SPACE_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        " ", "content",
        List.of("imagekey1", "imagekey2"));

    // 모집글 content 유효성 검사
    public static final PostRecruitRequest NULL_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", null,
        List.of("imagekey1", "imagekey2"));

    public static final PostRecruitRequest EMPTY_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", "",
        List.of("imagekey1", "imagekey2"));

    public static final PostRecruitRequest SPACE_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", " ",
        List.of("imagekey1", "imagekey2"));

    // 모집글 수정 테이스 데이터
    public static final UpdateRecruitRequest VALID_UPDATE_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "title", "content", List.of("https://image.ssuclubber.com/image2"),
        List.of("newImage1", "newImage2"), List.of("https://image.ssuclubber.com/image1"),
        List.of("newImage2", "https://image.ssuclubber.com/image1", "newImage1"));

}
