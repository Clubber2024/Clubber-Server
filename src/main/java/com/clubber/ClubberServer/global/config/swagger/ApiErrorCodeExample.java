package com.clubber.ClubberServer.global.config.swagger;

import com.clubber.ClubberServer.global.exception.BaseErrorCode;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiErrorCodeExample {

	Class<? extends BaseErrorCode> value();
}
