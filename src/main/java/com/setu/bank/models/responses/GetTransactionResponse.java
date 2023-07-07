package com.setu.bank.models.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.setu.bank.models.dtos.TransactionDto;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "createAccountResponse")
@Getter
public class GetTransactionResponse extends BaseResponse{

    private List<TransactionDto> transactions;
    
    public GetTransactionResponse(Status status) {
        super(status);
    }

    public GetTransactionResponse(List<TransactionDto> txns) {
        this.transactions = txns;
    }

}
