package com.madiot.enterprise.common.exception;

/**
 * Created by DELL on 2016/6/26.
 */
public class RestException extends Exception {

    public RestException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestException(String message) {
        super(message);
    }
}
