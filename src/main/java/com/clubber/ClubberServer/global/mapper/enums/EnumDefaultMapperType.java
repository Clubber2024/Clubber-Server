package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumDefaultMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType<EnumMapperVO> {
	@Override
	default EnumDefaultMapperVO createVO() {
		return new EnumDefaultMapperVO(this);
	}
}
