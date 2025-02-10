package com.clubber.ClubberServer.global.validator.unique;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Unique {

	String message() default "리뷰 작성 시 중복된 키워드는 불가능합니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
