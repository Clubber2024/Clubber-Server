package com.clubber.ClubberServer.global.vo.enums;

import com.clubber.ClubberServer.global.mapper.enums.EnumDefaultMapperType;
import lombok.Getter;

@Getter
public class EnumDefaultMapperVO extends EnumMapperVO {

	public EnumDefaultMapperVO(EnumDefaultMapperType enumMapperType) {
		super(enumMapperType);
	}
}
