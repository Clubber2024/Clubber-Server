package com.clubber.ClubberServer.domain.admin.dto;

import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.club.domain.ClubType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateAdminSignUpRequest {

	@NotBlank(message = "아이디를 입력하세요")
	private String username;

	@NotBlank(message = "비밀번호를 입력하세요")
	private String password;

	@NotBlank(message = "동아리/소모임 종류를 선택하세요")
	private ClubType clubType; 

	@NotBlank(message = "동아리 명을 입력하세요")
	private String clubName;

	@Email(message = "올바른 이메일 형식으로 입력하세요")
	private String email;

	@NotBlank(message = "승인 결과를 전달을 위한 동아리 Contact 채널을 입력하세요")
	private String contact;

	private String imageForApproval;

	public PendingAdminInfo toEntity(String encodedPassword) {
		return PendingAdminInfo.builder()
			.username(username)
			.password(encodedPassword)
			.clubName(clubName)
			.email(email)
			.contact(contact)
			.imageForApproval(imageForApproval)
			.build();
	}
}
