package com.clubber.ClubberServer.domain.admin.domain;

import com.clubber.ClubberServer.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PendingAdminInfo extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	private String username;

	@NotNull
	private String password;

	@NotNull
	private String clubName;

	@NotNull
	private String email;

	@NotNull
	private String contact;

	private String imageForApproval;

	private boolean isApproved = false;

	@Builder
	public PendingAdminInfo(Long id, String username, String password, String clubName,
		String email,
		String contact, String imageForApproval) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.clubName = clubName;
		this.email = email;
		this.contact = contact;
		this.imageForApproval = imageForApproval;
	}
}
