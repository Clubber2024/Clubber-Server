package com.clubber.global.enums;


import com.clubber.global.vo.enums.EnumMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType {

    @Override
    default EnumMapperVO createVO() {
        return new EnumMapperVO(this);
    }
}
