package com.setu.bank.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidTransactionException extends BaseException{
    
    public InvalidTransactionException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

}
