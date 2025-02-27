package com.nhnacademy.blog.common.exception;

public class BadRequestException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 400;

    public BadRequestException() {
        super(HTTP_STATUS_CODE, "Bad Request");
    }

    public BadRequestException(String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
