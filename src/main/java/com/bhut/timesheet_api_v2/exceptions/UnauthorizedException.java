package com.bhut.timesheet_api_v2.exceptions;

public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException() {
        super("Unauthorized user");
    }
}
