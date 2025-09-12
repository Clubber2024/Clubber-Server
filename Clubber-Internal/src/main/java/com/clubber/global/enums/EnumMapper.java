package com.clubber.global.enums;

import com.clubber.global.vo.enums.EnumMapperVO;
import lombok.NoArgsConstructor;

import java.util.*;

@NoArgsConstructor
public class EnumMapper {

    private final Map<String, List<EnumMapperVO>> factory = new LinkedHashMap<>();

    public void put(String key, Class<? extends EnumMapperType> e) {
        factory.put(key, toEnumValues(e));
    }

    private List<EnumMapperVO> toEnumValues(Class<? extends EnumMapperType> enumClass) {
        EnumMapperType[] enumConstants = enumClass.getEnumConstants();
        return Arrays.stream(enumConstants)
                .map(EnumMapperType::createVO)
                .toList();
    }

    public List<EnumMapperVO> toEnumValues(Set<? extends EnumMapperType> enums) {
        return enums.stream()
                .map(EnumMapperType::createVO)
                .toList();
    }

    public List<EnumMapperVO> get(String key) {
        return factory.get(key);
    }
}
