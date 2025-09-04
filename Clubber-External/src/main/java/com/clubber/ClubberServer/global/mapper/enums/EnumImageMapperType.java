package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;

public interface EnumImageMapperType extends EnumMapperType {

	String getImageUrl();

	@Override
	default EnumMapperVO createVO() {
		return new EnumImageMapperVO(this);
	}
}

