package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Contact;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import com.clubber.ClubberServer.domain.club.domain.College;
import com.clubber.ClubberServer.domain.club.domain.Department;
import com.clubber.ClubberServer.global.validator.enums.Enum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateAdminSignUpRequest {

    @Schema(description = "아이디")
    @NotBlank(message = "아이디를 입력하세요")
    private String username;

    @Schema(description = "비밀번호")
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    @Schema(description = "동아리 종류", example = "CENTER")
    @Enum(target = ClubType.class, message = "동아리 종류 입력값을 확인하세요")
    private ClubType clubType;

    @Schema(description = "소모임 단과대", example = "IT")
    private College college;

    @Schema(description = "소모임 학과", example = "AI")
    private Department department;

    @Schema(description = "동아리 명 (검색, 직접 입력 공통)")
    @NotBlank(message = "동아리 명을 입력하세요")
    private String clubName;

    @Schema(description = "이메일")
    @Email(message = "올바른 이메일 형식으로 입력하세요")
    private String email;

    @Schema(description = "동아리 공식 연락 수단 (인스타그램, 기타)")
    private Contact contact;

    @Schema(description = "승인을 위한 이미지")
    private String imageForApproval;

    @Schema(description = "인증 완료한 수행한 인증번호")
    private Integer authCode;

    public CreateAdminSignUpRequest() {
        clubType = ClubType.ETC;
        college = College.ETC;
        department = Department.ETC;
    }

    public PendingAdminInfo toEntity(String encodedPassword) {
        return PendingAdminInfo.builder()
                .username(username)
                .password(encodedPassword)
                .clubType(clubType)
                .college(college)
                .department(department)
                .clubName(clubName)
                .email(email)
                .contact(contact)
                .imageForApproval(imageForApproval)
                .build();
    }
}
