package com.clubber.ClubberServer.global.mapper.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapper {

	private final Map<String, List<EnumMapperVO>> factory = new LinkedHashMap<>();

	private final Map<String, Class<? extends EnumMapperType>> typeFactory = new LinkedHashMap<>();

	public void put(String key, Class<? extends EnumMapperType> e) {
		typeFactory.put(key, e);
		factory.put(key, toEnumValues(e));
	}

	private List<EnumMapperVO> toEnumValues(Class<? extends EnumMapperType> enumClass) {
		EnumMapperType[] enumConstants = enumClass.getEnumConstants();
		return Arrays.stream(enumConstants)
			.map(EnumMapperType::createVO)
			.toList();
	}

	public List<EnumMapperVO> get(String key) {
		return factory.get(key);
	}
}
