package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.domain.domains.review.exception.ReviewAlreadyDeletedException;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.common.vo.image.ImageVO;
import com.clubber.domain.common.BaseEntity;
import com.clubber.domain.domains.club.domain.Club;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.ArrayList;
import java.util.List;

import static com.clubber.ClubberServer.domain.review.domain.ApprovedStatus.DELETED;

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
			.content(content)
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

	public void delete() {
		if (approvedStatus == DELETED) {
			throw ReviewAlreadyDeletedException.EXCEPTION;
		}
		this.approvedStatus = DELETED;
	}
}
