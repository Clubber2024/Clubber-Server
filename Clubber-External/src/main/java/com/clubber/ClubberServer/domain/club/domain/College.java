package com.clubber.ClubberServer.domain.club.domain;

import com.clubber.common.mapper.enums.EnumDefaultMapperType;
import java.util.EnumSet;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum College implements EnumDefaultMapperType {
	IT_COLLEGE("IT대학",
		EnumSet.of(Department.IT, Department.COMPUTER_SCIENCE, Department.GLOBAL_MEDIA,
			Department.AI, Department.ELECTRONIC_INFORMATION, Department.SOFTWARE)),
	BUSINESS_COLLEGE("경영대학",
		EnumSet.of(Department.BUSINESS, Department.BUSINESS_ADMINISTRATION, Department.ACCOUNTING,
			Department.VENTURE_BUSINESS, Department.WELFARE_BUSINESS,
			Department.SMALL_MEDIUM_BUSINESS, Department.FINANCE, Department.INNOVATION_BUSINESS)),
	ECONOMICS_TRADE_COLLEGE("경제통상대학",
		EnumSet.of(Department.ECONOMICS_TRADE, Department.ECONOMICS, Department.FINANCE_ECONOMICS,
			Department.GLOBAL_TRADE, Department.INTERNATIONAL_TRADE)),
	ENGINEERING_COLLEGE("공과대학", EnumSet.of(Department.ENGINEERING, Department.CHEMICAL_ENGINEERING,
		Department.ELECTRICAL_ENGINEERING, Department.ARCHITECTURE, Department.INDUSTRIAL_SYSTEMS,
		Department.MECHANICAL_ENGINEERING, Department.MATERIALS_ENGINEERING)),
	LAW_COLLEGE("법과대학",
		EnumSet.of(Department.LAW_COLLEGE, Department.LAW, Department.INTERNATIONAL_LAW)),
	SOCIAL_SCIENCES_COLLEGE("사회과학대학",
		EnumSet.of(Department.SOCIAL_SCIENCES, Department.SOCIAL_WELFARE,
			Department.POLITICAL_SCIENCE, Department.JOURNALISM, Department.PUBLIC_ADMINISTRATION,
			Department.SOCIAL_INFORMATICS, Department.LIFELONG_EDUCATION)),
	HUMANITIES_COLLEGE("인문대학", EnumSet.of(Department.HUMANITIES, Department.THEOLOGY,
		Department.KOREAN_LANGUAGE_LITERATURE, Department.ENGLISH_LANGUAGE_LITERATURE,
		Department.FRENCH_LANGUAGE_LITERATURE, Department.CHINESE_LANGUAGE_LITERATURE,
		Department.JAPANESE_LANGUAGE_LITERATURE, Department.GERMAN_LANGUAGE_LITERATURE,
		Department.HISTORY, Department.SPORT, Department.PHILOSOPHY)),
	NATURAL_SCIENCES_COLLEGE("자연과학대학",
		EnumSet.of(Department.NATURAL_SCIENCES, Department.MATHEMATICS, Department.PHYSICS,
			Department.CHEMISTRY, Department.STATISTICS, Department.BIOLOGY)),
	ETC("해당 없음", EnumSet.of(Department.ETC));

	private final String college;
	private final EnumSet<Department> departments;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return college;

	}
}
