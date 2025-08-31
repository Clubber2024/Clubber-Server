package com.clubber.ClubberServer.domain.recruit.vo;

import com.clubber.ClubberServer.domain.recruit.domain.RecruitComment;
import com.clubber.ClubberServer.domain.recruit.dto.recruitComment.GetRecruitCommentResponse;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class RecruitCommentVO {
    private final List<GetRecruitCommentResponse> totalComments = new ArrayList<>();
    private final Map<Long, GetRecruitCommentResponse> commentMap = new HashMap<>();

    public void addToTreeStructure(GetRecruitCommentResponse response) {
        commentMap.put(response.getCommentId(), response);
    }

    public void updateInCommentResponse(RecruitComment parentComment, GetRecruitCommentResponse response) {
        if (parentComment == null) {
            totalComments.add(response);
        } else {
            GetRecruitCommentResponse parentRecruitResponse = commentMap.get(parentComment.getId());
            parentRecruitResponse.getReplies().add(response);
        }
    }
}
