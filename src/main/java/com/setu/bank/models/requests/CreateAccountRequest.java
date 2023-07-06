package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.AccountType;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String accountNumber;
    private AccountType accountType;
    private Long userId;
    private Double openingBalance;
}
