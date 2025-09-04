package com.clubber.ClubberInternal.global.enums;

import com.clubber.ClubberInternal.global.vo.enums.EnumImageMapperVO;
import com.clubber.ClubberInternal.global.vo.enums.EnumMapperVO;

public interface EnumImageMapperType extends EnumMapperType {

	String getImageUrl();

	@Override
	default EnumMapperVO createVO() {
		return new EnumImageMapperVO(this);
	}
}

