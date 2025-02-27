package com.nhnacademy.blog.common.exception;

public class ForbiddenException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 403;

    public ForbiddenException() {
        super(HTTP_STATUS_CODE,"forbidden");
    }

    public ForbiddenException(String message) {
        super(HTTP_STATUS_CODE, message);
    }
}
