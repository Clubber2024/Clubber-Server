package com.clubber.ClubberServer.domain.club.exception;

import com.clubber.ClubberServer.global.error.BaseException;

public class DepartmentNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new DepartmentNotFoundException();

    public DepartmentNotFoundException() {
        super(ClubErrorCode.DEPARTMENT_NOT_FOUND);
    }
}

