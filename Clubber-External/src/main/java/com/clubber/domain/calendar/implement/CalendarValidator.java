package com.clubber.domain.calendar.implement;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.calendar.domain.Calendar;
import com.clubber.domain.calendar.exception.CalendarPostUnauthorizedException;
import com.clubber.domain.calendar.exception.CalendarInvalidMonthException;
import org.springframework.stereotype.Component;

@Component
public class CalendarValidator {

    public void validateCalendarClub(Calendar calendar, Admin admin) {
        if (calendar.getClub() != admin.getClub()) {
            throw CalendarPostUnauthorizedException.EXCEPTION;
        }
    }

    public void validateCalendarMonth(int month) {
        if (month < 1 || month > 12) {
            throw CalendarInvalidMonthException.EXCEPTION;
        }
    }

}
