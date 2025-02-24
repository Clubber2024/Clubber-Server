package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.exception.BaseException;

public class DepartmentNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new DepartmentNotFoundException();

    private DepartmentNotFoundException() {
        super(ClubErrorCode.DEPARTMENT_NOT_FOUND);
    }
}

