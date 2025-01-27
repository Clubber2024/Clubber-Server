package com.clubber.ClubberServer.global.converter;

import com.clubber.ClubberServer.global.exception.EnumTypeNotValidException;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;
import jakarta.persistence.AttributeConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class EnumMapperTypeConverter implements AttributeConverter<EnumMapperType, String> {

	private final EnumMapper enumMapper;

	@Override
	public String convertToDatabaseColumn(EnumMapperType enumMapperType) {
		if (enumMapperType == null) {
			log.error("null값 Enum으로 저장시도");
			throw EnumTypeNotValidException.EXCEPTION;
		}
		return enumMapperType.getCode();
	}

	@Override
	public EnumMapperType convertToEntityAttribute(String dbData) {
		if (dbData == null) {
			log.error("Enum 타입에 null값 저장되어 있었음");
			throw EnumTypeNotValidException.EXCEPTION;
		}
		return enumMapper.getEnumInstance(dbData);
	}
}
