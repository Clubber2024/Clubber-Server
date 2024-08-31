package com.clubber.ClubberServer.global.enummapper;

import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EnumMapperTypeConverter implements AttributeConverter<EnumMapperType, String> {

	private final EnumMapper enumMapper;

	@Override
	public String convertToDatabaseColumn(EnumMapperType enumMapperType) {
		if (enumMapperType == null) {
			return null;
		}
		return enumMapperType.getCode();
	}

	@Override
	public EnumMapperType convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			return null;
		}
		return enumMapper.getEnumInstance(dbData);
	}
}
