package com.clubber.ClubberServer.domain.review.domain;

import com.clubber.domain.common.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class ReviewKeyword extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "review_id")
	@NotNull
	private Review review;

	@NotNull
	@JdbcTypeCode(SqlTypes.VARCHAR)
	@Enumerated(EnumType.STRING)
	private Keyword keyword;

	@Builder
	private ReviewKeyword(Long id, Review review, Keyword keyword) {
		this.id = id;
		this.review = review;
		this.keyword = keyword;
	}

	public static ReviewKeyword of(Keyword keyword, Review review) {
		return ReviewKeyword.builder()
			.keyword(keyword)
			.review(review)
			.build();
	}

	public String getKeywordTitle() {
		return keyword.getTitle();
	}
}
