package com.setu.bank.exceptions;

import org.springframework.http.HttpStatus;

public class BaseException extends Exception{
    public HttpStatus status;
    public String message;

    public BaseException(String message) {
        super(message);
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public BaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
