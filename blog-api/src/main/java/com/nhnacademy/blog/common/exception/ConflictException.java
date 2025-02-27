package com.nhnacademy.blog.common.exception;

public class ConflictException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 409;

    public ConflictException() {
        super(HTTP_STATUS_CODE, "Conflict with existing resource");
    }
    public ConflictException(String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
