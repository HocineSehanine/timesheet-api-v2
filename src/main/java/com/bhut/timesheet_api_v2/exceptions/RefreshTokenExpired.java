package com.bhut.timesheet_api_v2.exceptions;

public class RefreshTokenExpired extends RuntimeException {

    public RefreshTokenExpired() {
        super("Refresh token expired.");
    }
}
