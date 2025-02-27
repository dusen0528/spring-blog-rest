package com.nhnacademy.blog.common.exception;

public class UnauthorizedException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 401;

    public UnauthorizedException() {
        super(HTTP_STATUS_CODE, "Unauthorized");
    }

    public UnauthorizedException(String message) {
        super(HTTP_STATUS_CODE, message);
    }

}
