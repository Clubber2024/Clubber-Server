package com.clubber.domain.domains.club.exception;

import com.clubber.common.exception.BaseException;

public class DepartmentNotFoundException extends BaseException {
    public static final BaseException EXCEPTION = new DepartmentNotFoundException();

    private DepartmentNotFoundException() {
        super(ClubErrorCode.DEPARTMENT_NOT_FOUND);
    }
}

