package com.clubber.ClubberServer.global.enummapper;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
public class EnumMapperVO {
    private final String code;
    private final String title;

    public EnumMapperVO(EnumMapperType enumMapperType) {
        this.code = enumMapperType.getCode();
        this.title = enumMapperType.getTitle();
    }
}
