package com.setu.bank.exceptions;

import org.springframework.http.HttpStatus;

public class AccountNotFoundException extends BaseException{
    
    public AccountNotFoundException(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }

}
