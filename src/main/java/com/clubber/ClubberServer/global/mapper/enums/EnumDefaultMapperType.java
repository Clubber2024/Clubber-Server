package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumDefaultMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType {

    @Override
    default EnumMapperVO createVO() {
        return new EnumDefaultMapperVO(this);
    }
}
