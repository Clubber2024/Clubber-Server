package com.clubber.ClubberServer.domain.review.domain;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.*;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.util.StringUtils;

import com.clubber.ClubberServer.domain.admin.exception.InvalidApprovedStatusException;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.user.domain.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(indexes = @Index(name = "idx_review_club_id_approved_status_id_desc",
	columnList = "club_id, approved_status, id desc"))
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "club_id")
	@NotNull
	private Club club;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@NotNull
	private User user;

	private String content;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private ApprovedStatus approvedStatus;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
	private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

	@Builder
	private Review(Long id, Club club, User user, String content, ApprovedStatus approvedStatus) {
		this.id = id;
		this.club = club;
		this.user = user;
		this.content = content;
		this.approvedStatus = approvedStatus;
	}

	

	public static Review of(User user, Club club, String content) {
		return Review.builder()
			.user(user)
			.club(club)
			.content(hasContent(content) ? content : null)
			.approvedStatus(hasContent(content) ? PENDING : NULL_CONTENT)
			.build();
	}

	public void approve() {
		if (this.approvedStatus != ApprovedStatus.PENDING)
			throw InvalidApprovedStatusException.EXCEPTION;
		this.approvedStatus = ApprovedStatus.APPROVED;
	}

	public void reject() {
		if (this.approvedStatus != ApprovedStatus.PENDING)
			throw InvalidApprovedStatusException.EXCEPTION;
		this.approvedStatus = ApprovedStatus.REJECTED;
	}

	public void updateReviewStatus(ApprovedStatus approvedStatus) {
		if (this.approvedStatus != PENDING) {
			throw InvalidApprovedStatusException.EXCEPTION;
		}
		this.approvedStatus = approvedStatus; 
	}

	public void delete() {
		this.approvedStatus = DELETED;
	}

	private static boolean hasContent(String content){
		return StringUtils.hasText(content);
	}
}
