package com.clubber.ClubberServer.integration.util.fixture;

import com.clubber.domain.recruit.dto.recruitComment.PostRecruitCommentRequest;

public class RecruitCommentFixture {

    public static final PostRecruitCommentRequest VALID_COMMENT_REQUEST = new PostRecruitCommentRequest(
        "comment", null);

    public static final PostRecruitCommentRequest NULL_CONTENT_COMMENT_REQUEST = new PostRecruitCommentRequest(
        null, null);

    public static final PostRecruitCommentRequest EMPTY_CONTENT_COMMENT_REQUEST = new PostRecruitCommentRequest(
        "", null);

    public static final PostRecruitCommentRequest SPACE_CONTENT_COMMENT_REQUEST = new PostRecruitCommentRequest(
        " ", null);

    public static final PostRecruitCommentRequest VALID_REPLY_COMMENT_REQUEST = new PostRecruitCommentRequest(
        "replyComment", 1L);

    public static final PostRecruitCommentRequest NO_PARENT_REPLY_COMMENT_REQUEST = new PostRecruitCommentRequest(
        "replyComment", 5L);

    public static final PostRecruitCommentRequest INVALID_LENGTH_CONTENT_COMMENT_REQUEST = new PostRecruitCommentRequest(
        "안녕하세요, 클러버 동아리에 대해 궁금한 점이 있어서 문의드립니다. "
            + "신입 회원 모집 기간과 지원 방법, 그리고 동아리 활동 내용에 대해 자세히 알고 싶습니다. "
            + "특히 정기 모임 일정과 주요 프로젝트, 멘토링 기회가 있는지도 궁금합니다. "
            + "자세한 답변 부탁드립니다. 감사합니다!",
        1L);


}
