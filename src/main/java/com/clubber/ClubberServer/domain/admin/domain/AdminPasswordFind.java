package com.clubber.ClubberServer.domain.admin.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "adminPasswordFind")
public class AdminPasswordFind {

    @Id
    private Long id;

    private String email;

    private Integer authCode;

    private Long ttl = 300L;
}
