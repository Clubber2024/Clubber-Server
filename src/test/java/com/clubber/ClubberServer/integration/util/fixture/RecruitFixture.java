package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitType;
import com.clubber.ClubberServer.domain.recruit.dto.PostRecruitRequest;
import com.clubber.ClubberServer.domain.recruit.dto.UpdateRecruitRequest;
import java.util.List;

public class RecruitFixture {

    // 모집글 작성 테스트 데이터
    public static final PostRecruitRequest VALID_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", RecruitType.REGULAR, "content", "linkedUrl",
        List.of("imagekey1", "imagekey2"), false);

    // 모집글 title 유효성 검사
    public static final PostRecruitRequest NULL_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        null, RecruitType.REGULAR, "content", "linkedUrl",
        List.of("imagekey1", "imagekey2"), false);

    public static final PostRecruitRequest EMPTY_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "", RecruitType.REGULAR, "content", "linkedUrl",
        List.of("imagekey1", "imagekey2"),false);

    public static final PostRecruitRequest SPACE_TITLE_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        " ", RecruitType.REGULAR, "content", "linkedUrl",
        List.of("imagekey1", "imagekey2"),false);

    // 모집글 content 유효성 검사
    public static final PostRecruitRequest NULL_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", RecruitType.REGULAR, null, "linkedUrl",
        List.of("imagekey1", "imagekey2"),false);

    public static final PostRecruitRequest EMPTY_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", RecruitType.REGULAR, "", "linkedUrl",
        List.of("imagekey1", "imagekey2"),false);

    public static final PostRecruitRequest SPACE_CONTENT_RECRUIT_POST_REQUEST = new PostRecruitRequest(
        "title", RecruitType.REGULAR, " ", "linkedUrl",
        List.of("imagekey1", "imagekey2"),false);

    // 모집글 수정 테이스 데이터

    // 성공 데이터
    public static final UpdateRecruitRequest VALID_UPDATE_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR, "newContent", "newLinkedUrl", false, List.of("https://image.ssuclubber.com/image2"),
        List.of("newImage1", "newImage2"), List.of("https://image.ssuclubber.com/image1"),
        List.of("newImage2", "https://image.ssuclubber.com/image1", "newImage1"));

    public static final UpdateRecruitRequest UPDATE_NO_IMAGE_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR, "newContent", "newLinkedUrl", false, List.of(),
        List.of("newImage1", "newImage2"), List.of(),
        List.of("newImage2", "newImage1"));

    // 실패 데이터

    // 존재하지 않는 이미지 삭제 처리
    public static final UpdateRecruitRequest INVALID_DELETE_IMAGE_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR, "newContent","newLinkedUrl", false,
        List.of("https://image.ssuclubber.com/image1", "https://image.ssuclubber.com/image3"),
        List.of("newImage1", "newImage2"), List.of("https://image.ssuclubber.com/image1"),
        List.of("newImage2", "https://image.ssuclubber.com/image1", "newImage1"));

    // 존재하지 않는 이미지에 대해 유지 처리
    public static final UpdateRecruitRequest REMAIN_NOT_EXIST_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR, "newContent", "newEverytimeUrl", false,
        List.of("https://image.ssuclubber.com/image2"),
        List.of("newImage1", "newImage2"),
        List.of("https://image.ssuclubber.com/image1", "https://image.ssuclubber.com/image3"),
        List.of("newImage2", "https://image.ssuclubber.com/image1", "newImage1"));

    // 삭제할 이미지와 유지할 이미지 중복 지정
    public static final UpdateRecruitRequest REMAIN_DELETE_DUPLICATED_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR, "newContent","newEverytimeUrl", false,
        List.of("https://image.ssuclubber.com/image1"),
        List.of("newImage1", "newImage2"), List.of("https://image.ssuclubber.com/image1"),
        List.of("newImage2", "https://image.ssuclubber.com/image1", "newImage1"));

    // 추가 데이터와 최종 데이터 불일치
    public static final UpdateRecruitRequest NEW_FINAL_IMAGE_DIFFERENT_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR,"newContent","newEverytimeUrl", false,
        List.of(),
        List.of("newImage1", "newImage2"), List.of(),
        List.of("newImage2", "newImage3"));

    public static final UpdateRecruitRequest NEW_NOT_IN_FINAL_IMAGE_RECRUIT_REQUEST = new UpdateRecruitRequest(
        "newTitle", RecruitType.REGULAR,"newContent","newEverytimeUrl",false,
        List.of(),
        List.of("newImage1", "newImage2", "newImage3"), List.of(),
        List.of("newImage2", "newImage1"));


}
