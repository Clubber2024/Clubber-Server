package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapperType;
import com.clubber.ClubberServer.global.vo.enums.EnumDefaultMapperVO;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Division implements EnumDefaultMapperType {
	EDUCATION("교양분과"),
	BUSINESS("연대사업분과"),
	ART("연행예술분과"),
	RELIGION("종교분과"),
	CREATIVE_EXHIBITION("창작전시분과"),
	SPORTS("체육분과"),
	ACADEMIC("학술분과"),
	ETC("해당 없음");

	private final String division;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return division;
	}
}
