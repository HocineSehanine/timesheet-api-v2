package com.bhut.timesheet_api_v2.exceptions;

public class BadRequestException extends RuntimeException {

    private final int statusCode = 400;
    private final String code;
    private final String message;

    public BadRequestException() {
        super("Request body required fields.");
        this.code = "REQUIRED_FIELDS";
        this.message = "Request body required fields.";
    }

    public BadRequestException(final String message) {
        super(message);
        this.code = "REQUIRED_FIELDS";
        this.message = message;
    }

    public BadRequestException(final String code, final String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
