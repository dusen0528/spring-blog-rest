package com.nhnacademy.blog.common.exception;

public class NotFoundException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 404;

    public NotFoundException() {
        super(HTTP_STATUS_CODE,"resource not found");
    }

    public NotFoundException(String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
