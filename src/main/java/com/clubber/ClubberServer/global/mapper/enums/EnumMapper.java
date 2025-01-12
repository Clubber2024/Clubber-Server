package com.clubber.ClubberServer.global.mapper.enums;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapper {

	private Map<String, List<EnumMapperVO>> factory = new LinkedHashMap<>();

	private Map<String, Class<? extends EnumMapperType>> typeFactory = new LinkedHashMap<>();

	public void put(String key, Class<? extends EnumMapperType> e) {
		typeFactory.put(key, e);
		factory.put(key, toEnumValues(e));
	}

	public Class<? extends EnumMapperType> getEnumType(String key) {
		return typeFactory.get(key);
	}

	public EnumMapperType getEnumInstance(String key) {
		Class<? extends EnumMapperType> enumType = getEnumType(key);
		EnumMapperType[] enumConstants = enumType.getEnumConstants();
		for (EnumMapperType enumConstant : enumConstants) {
			if (enumConstant.getCode().equals(key)) {
				return enumConstant;
			}
		}
		return null;
	}

	public List<EnumMapperVO> toEnumValues(Class<? extends EnumMapperType> e) {
		return Arrays.stream(e.getEnumConstants())
			.map(EnumMapperVO::new)
			.collect(Collectors.toList());
	}

	public List<EnumMapperVO> toEnumValues(List<? extends EnumMapperType> enumMapperTypes) {
		return enumMapperTypes.stream()
			.map(EnumMapperVO::new)
			.collect(Collectors.toList());
	}

	public List<EnumMapperVO> get(String key) {
		return factory.get(key);
	}

}
