package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.kakao.dto;

import feign.form.FormProperty;
import lombok.Getter;

@Getter
public class UnlinkKaKaoTarget {
    @FormProperty("target_id_type")
    private String targetIdType = "user_id";

    @FormProperty("target_id")
    private Long target_id;

    public UnlinkKaKaoTarget(Long target_id) {
        this.target_id = target_id;
    }

    public static UnlinkKaKaoTarget from(Long snsId){
        return new UnlinkKaKaoTarget(snsId);
    }
}
