package com.clubber.ClubberServer.domain.example;

import com.clubber.ClubberServer.domain.admin.exception.AdminErrorCode;
import com.clubber.ClubberServer.domain.club.exception.ClubErrorCode;
import com.clubber.ClubberServer.domain.favorite.exception.FavoriteErrorCode;
import com.clubber.ClubberServer.domain.notice.exception.NoticeErrorCode;
import com.clubber.ClubberServer.domain.recruit.exception.RecruitErrorCode;
import com.clubber.ClubberServer.domain.review.exception.ReviewErrorCode;
import com.clubber.ClubberServer.domain.user.exception.UserErrorCode;
import com.clubber.ClubberServer.global.config.swagger.ApiErrorCodeExample;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/example")
public class ExampleController {

    @GetMapping("/admin")
    @ApiErrorCodeExample(AdminErrorCode.class)
    public void getAdminErrorExample() {}

    @GetMapping("/club")
    @ApiErrorCodeExample(ClubErrorCode.class)
    public void getClubErrorExample() {}

    @GetMapping("/favorite")
    @ApiErrorCodeExample(FavoriteErrorCode.class)
    public void getFavoriteErrorExample() {}

    @GetMapping("/recruit")
    @ApiErrorCodeExample(RecruitErrorCode.class)
    public void getRecruitErrorExample() {}

    @GetMapping("/review")
    @ApiErrorCodeExample(ReviewErrorCode.class)
    public void getReviewErrorExample() {}

    @GetMapping("/user")
    @ApiErrorCodeExample(UserErrorCode.class)
    public void getUserErrorExample() {}

    @GetMapping("/notice")
    @ApiErrorCodeExample(NoticeErrorCode.class)
    public void getNoticeErrorExample() {}
}
