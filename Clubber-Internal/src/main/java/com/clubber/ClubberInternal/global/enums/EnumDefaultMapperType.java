package com.clubber.ClubberInternal.global.enums;


import com.clubber.ClubberInternal.global.vo.enums.EnumMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType {

    @Override
    default EnumMapperVO createVO() {
        return new EnumMapperVO(this);
    }
}
