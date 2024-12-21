package com.clubber.ClubberServer.global.validator.unique;

import com.clubber.ClubberServer.domain.review.domain.Keyword;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, List<Keyword>> {
    @Override
    public boolean isValid(List<Keyword> keywords, ConstraintValidatorContext constraintValidatorContext) {
        return new HashSet<>(keywords).size() == keywords.size();
    }
}
