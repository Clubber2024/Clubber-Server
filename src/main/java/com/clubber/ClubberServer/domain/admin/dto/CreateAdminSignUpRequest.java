package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.Contact;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminSignUpRequest {

	@Schema(description = "아이디")
	@NotBlank(message = "아이디를 입력하세요")
	private String username;

	@Schema(description = "비밀번호")
	@NotBlank(message = "비밀번호를 입력하세요")
	private String password;

	@Schema(description = "동아리 종류", example = "CENTER, SMALL, OFFICIAL, GENERAL")
	@NotBlank(message = "동아리/소모임 종류를 선택하세요")
	private ClubType clubType; 

	@Schema(description = "동아리 명 (검색, 직접 입력 공통)")
	@NotBlank(message = "동아리 명을 입력하세요")
	private String clubName;

	@Schema(description = "이메일")
	@Email(message = "올바른 이메일 형식으로 입력하세요")
	private String email;

	@Schema(description = "동아리 공식 연락 수단 (인스타그램, 기타)")
	@NotBlank(message = "승인 결과를 전달을 위한 동아리 Contact 채널을 입력하세요")
	private Contact contact;

	@Schema(description = "승인을 위한 이미지")
	private String imageForApproval;

	public PendingAdminInfo toEntity(String encodedPassword) {
		return PendingAdminInfo.builder()
			.username(username)
			.password(encodedPassword)
			.clubType(clubType)
			.clubName(clubName)
			.email(email)
			.contact(contact)
			.imageForApproval(imageForApproval)
			.build();
	}
}
