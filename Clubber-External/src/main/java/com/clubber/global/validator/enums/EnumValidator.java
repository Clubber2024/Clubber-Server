package com.clubber.global.validator.enums;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValidator implements ConstraintValidator<Enum, java.lang.Enum> {

	private Enum annotation;

	@Override
	public void initialize(Enum constraintAnnotation) {
		this.annotation = constraintAnnotation;
	}

	@Override
	public boolean isValid(java.lang.Enum value,
		ConstraintValidatorContext constraintValidatorContext) {
		return value != null;
	}
}
