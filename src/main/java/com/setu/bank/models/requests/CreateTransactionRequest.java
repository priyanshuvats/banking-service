package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.TransactionType;

import lombok.Data;

@Data
public class CreateTransactionRequest {
    
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;

}
