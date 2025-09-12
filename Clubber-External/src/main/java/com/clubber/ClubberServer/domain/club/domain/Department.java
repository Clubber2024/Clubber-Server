package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Department implements EnumDefaultMapperType {

	// IT대학 학과
	IT("IT대 소속"),
	COMPUTER_SCIENCE("컴퓨터학부"),
	GLOBAL_MEDIA("글로벌미디어학부"),
	AI("AI융합학부"),
	ELECTRONIC_INFORMATION("전자정보공학부"),
	SOFTWARE("소프트웨어학부"),


	// 경영대학 학과
	BUSINESS("경영대학 소속"),
	BUSINESS_ADMINISTRATION("경영학부"),
	ACCOUNTING("회계학과"),
	VENTURE_BUSINESS("벤처경영학과"),
	WELFARE_BUSINESS("복지경영학과"),
	SMALL_MEDIUM_BUSINESS("벤처중소기업학과"),
	FINANCE("금융학부"),
	INNOVATION_BUSINESS("혁신경영학과"),

	// 경제통상대학 학과
	ECONOMICS_TRADE("경제통상대학 소속"),
	ECONOMICS("경제학과"),
	FINANCE_ECONOMICS("금융경제학과"),
	GLOBAL_TRADE("글로벌통상학과"),
	INTERNATIONAL_TRADE("국제무역학과"),

	// 공과대학 학과
	ENGINEERING("공과대학 소속"),
	CHEMICAL_ENGINEERING("화학공학과"),
	ELECTRICAL_ENGINEERING("전기공학부"),
	ARCHITECTURE("건축학부"),
	INDUSTRIAL_SYSTEMS("산업정보시스템공학과"),
	MECHANICAL_ENGINEERING("기계공학부"),
	MATERIALS_ENGINEERING("신소재공학과"),

	// 법과대학 학과
	LAW_COLLEGE("법과대학 소속"),
	LAW("법학과"),
	INTERNATIONAL_LAW("국제법무학과"),

	// 사회과학대학 학과
	SOCIAL_SCIENCES("사회과학대학 소속"),
	SOCIAL_WELFARE("사회복지학부"),
	POLITICAL_SCIENCE("정치외교학과"),
	JOURNALISM("언론홍보학과"),
	PUBLIC_ADMINISTRATION("행정학부"),
	SOCIAL_INFORMATICS("정보사회학과"),
	LIFELONG_EDUCATION("평생교육학과"),

	// 인문대학 학과
	HUMANITIES("인문대학 소속"),
	THEOLOGY("기독교학과"),
	KOREAN_LANGUAGE_LITERATURE("국어국문학과"),
	ENGLISH_LANGUAGE_LITERATURE("영어영문학과"),
	FRENCH_LANGUAGE_LITERATURE("불어불문학과"),
	CHINESE_LANGUAGE_LITERATURE("중어중문학과"),
	JAPANESE_LANGUAGE_LITERATURE("일어일문학과"),
	GERMAN_LANGUAGE_LITERATURE("독어독문학과"),
	HISTORY("사학과"),
	SPORT("스포츠학부"),
	PHILOSOPHY("철학과"),

	// 자연과학대학 학과
	NATURAL_SCIENCES("자연과학대학 소속"),
	MATHEMATICS("수학과"),
	PHYSICS("물리학과"),
	CHEMISTRY("화학과"),
	STATISTICS("정보통계보험수리학과"),
	BIOLOGY("의생명시스템학부"),

	// 그외
	ETC("해당 없음");

	private final String department;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return department;
	}
}

