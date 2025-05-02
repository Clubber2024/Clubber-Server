package com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.kakao.dto;


import lombok.Getter;

@Getter
public class UnlinkKaKaoTarget {
    @feign.form.FormProperty("target_id_type")
    private String targetIdType = "user_id";

    @feign.form.FormProperty("target_id")
    private Long target_id;

    public UnlinkKaKaoTarget(Long target_id) {
        this.target_id = target_id;
    }

    public static UnlinkKaKaoTarget from(Long snsId){
        return new UnlinkKaKaoTarget(snsId);
    }
}
