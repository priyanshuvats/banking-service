package com.setu.bank.models.responses;

import com.setu.bank.models.dtos.TransactionDto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class CreateTransactionResponse extends BaseResponse{

    private TransactionDto transaction;

    public CreateTransactionResponse(Status status) {
        super(status);
    }

    public CreateTransactionResponse(TransactionDto txn) {
        this.transaction = txn;
    }
    
}
