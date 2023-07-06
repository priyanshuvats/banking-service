package com.setu.bank.models.responses;

import java.util.List;

import com.setu.bank.models.entities.Transaction;

public class GetTransactionResponse extends BaseResponse{

    private List<Transaction> transactions;
    
    public GetTransactionResponse(Status status) {
        super(status);
    }

    public GetTransactionResponse(List<Transaction> txns) {
        this.transactions = txns;
    }

}
