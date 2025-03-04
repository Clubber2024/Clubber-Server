package com.clubber.ClubberServer.domain.review.domain;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.APPROVED;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.DELETED;
import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.PENDING;
import static com.clubber.ClubberServer.domain.review.domain.VerifiedStatus.VERIFIED;

import com.clubber.ClubberServer.domain.admin.exception.InvalidApprovedStatusException;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.common.BaseEntity;
import com.clubber.ClubberServer.domain.review.exception.ReviewAlreadyDeletedException;
import com.clubber.ClubberServer.domain.review.exception.ReviewAlreadyVerifiedException;
import com.clubber.ClubberServer.domain.review.util.ReviewUtil;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.global.vo.image.ImageVO;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embedded;
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
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

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
	@NotNull
	private ApprovedStatus approvedStatus;

	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	@NotNull
	private VerifiedStatus verifiedStatus = VerifiedStatus.NOT_VERIFIED;

	@Embedded
	private ImageVO authImageVo;

	@OneToMany(mappedBy = "review", cascade = CascadeType.ALL)
	private List<ReviewKeyword> reviewKeywords = new ArrayList<>();

	@Builder
	private Review(Long id, Club club, User user, String content, ApprovedStatus approvedStatus,
		ImageVO imageVO) {
		this.id = id;
		this.club = club;
		this.user = user;
		this.content = content;
		this.approvedStatus = approvedStatus;
		this.authImageVo = imageVO;
	}

	public static Review of(User user, Club club, String content, String authImage) {
		return Review.builder()
			.user(user)
			.club(club)
			.content(ReviewUtil.checkBlankContent(content))
			.approvedStatus(ReviewUtil.checkBlankContentApprovedStatus(content))
			.imageVO(ImageVO.valueOf(authImage))
			.build();
	}

	//양방향 매핑 메서드
	public void addKeywords(List<Keyword> keywords) {
		keywords.forEach(keyword -> {
			ReviewKeyword reviewKeyword = ReviewKeyword.of(keyword, this);
			this.reviewKeywords.add(reviewKeyword);
		});
	}

	public void updateReviewStatus(ApprovedStatus approvedStatus) {
		if (this.approvedStatus != PENDING) {
			throw InvalidApprovedStatusException.EXCEPTION;
		}
		this.approvedStatus = approvedStatus;
	}

	public void autoUpdateReviewStatus() {
		if (this.approvedStatus == PENDING) {
			this.approvedStatus = APPROVED;
		}
	}

	public void delete() {
		if (approvedStatus == DELETED) {
			throw ReviewAlreadyDeletedException.EXCEPTION;
		}
		this.approvedStatus = DELETED;
	}

	public void verify() {
		if (verifiedStatus == VERIFIED) {
			throw ReviewAlreadyVerifiedException.EXCEPTION;
		}
		this.verifiedStatus = VERIFIED;
	}

	public String getContentForUser() {
		if (approvedStatus == APPROVED) {
			return content;
		}
		return null;
	}
}
