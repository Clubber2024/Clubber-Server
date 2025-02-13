package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapper<T extends EnumMapperVO, E extends EnumMapperType<T>> {

	private final Map<String, List<T>> factory = new LinkedHashMap<>();

	private final Map<String, Class<? extends E>> typeFactory = new LinkedHashMap<>();

	public void put(String key, Class<? extends E> e) {
		typeFactory.put(key, e);
		factory.put(key, toEnumValues(e));
	}

	private List<T> toEnumValues(Class<? extends E> enumClass) {
		E[] enumConstants = enumClass.getEnumConstants();
		return Arrays.stream(enumConstants)
			.map(EnumMapperType::createVO)
			.toList();
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

	public Class<? extends E> getEnumType(String key) {
		return typeFactory.get(key);
	}

	public List<T> toEnumValues(List<? extends E> enumMapperTypes) {
		return enumMapperTypes.stream()
			.map(E::createVO)
			.collect(Collectors.toList());
	}

	public List<T> get(String key) {
		return factory.get(key);
	}

}
