package com.clubber.ClubberServer.domain.club.exception;

public class ClubNotFoundException extends RuntimeException{
    public ClubNotFoundException(String message) {
        super(message);
    }

}
