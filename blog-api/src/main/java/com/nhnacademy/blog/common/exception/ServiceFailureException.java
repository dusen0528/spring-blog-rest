package com.nhnacademy.blog.common.exception;

public class ServiceFailureException extends CommonHttpException {
    private static final int HTTP_STATUS_CODE = 500;

    public ServiceFailureException() {
        super(HTTP_STATUS_CODE,"service failure");
    }

    public ServiceFailureException(String message) {
        super(HTTP_STATUS_CODE, message);
    }

    public ServiceFailureException(String message, Throwable cause) {
        super(HTTP_STATUS_CODE, message, cause);
    }
}
