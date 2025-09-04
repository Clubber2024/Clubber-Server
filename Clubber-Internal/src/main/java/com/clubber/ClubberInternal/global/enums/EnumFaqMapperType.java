package com.clubber.ClubberInternal.global.enums;

import com.clubber.ClubberInternal.global.vo.enums.EnumFaQMapperVO;
import com.clubber.ClubberInternal.global.vo.enums.EnumMapperVO;

public interface EnumFaqMapperType extends EnumMapperType {

	String getAnswer();

	@Override
	default EnumMapperVO createVO() {
		return new EnumFaQMapperVO(this);
	}
}
