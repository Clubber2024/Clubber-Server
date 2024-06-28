package com.clubber.ClubberServer.global.enummapper;


import java.util.Arrays;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EnumMapper {

    private Map<String, List<EnumMapperVO>> factory = new LinkedHashMap<>();

    public void put(String key, Class<? extends EnumMapperType> e) {
        factory.put(key, toEnumValues(e));
    }

    public List<EnumMapperVO> toEnumValues(Class<? extends EnumMapperType> e) {
        return Arrays.stream(e.getEnumConstants())
                .map(EnumMapperVO::new)
                .collect(Collectors.toList());
    }

}
