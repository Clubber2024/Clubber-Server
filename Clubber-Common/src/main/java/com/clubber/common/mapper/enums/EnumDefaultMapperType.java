package com.clubber.common.mapper.enums;

import com.clubber.common.vo.enums.EnumMapperVO;

public interface EnumDefaultMapperType extends EnumMapperType {

    @Override
    default EnumMapperVO createVO() {
        return new EnumMapperVO(this);
    }
}
