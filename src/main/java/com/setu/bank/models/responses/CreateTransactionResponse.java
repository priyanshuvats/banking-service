package com.setu.bank.models.responses;

import com.setu.bank.models.entities.Transaction;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class CreateTransactionResponse extends BaseResponse{

    private Transaction transaction;

    public CreateTransactionResponse(Status status) {
        super(status);
    }

    public CreateTransactionResponse(Transaction txn) {
        this.transaction = txn;
    }
    
}
