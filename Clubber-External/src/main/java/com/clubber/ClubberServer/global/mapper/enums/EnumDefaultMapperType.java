package com.clubber.ClubberServer.global.mapper.enums;

import com.clubber.ClubberServer.global.vo.enums.EnumMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType {

    @Override
    default EnumMapperVO createVO() {
        return new EnumMapperVO(this);
    }
}
