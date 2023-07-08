package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.TransactionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateTransactionRequest {
    @ApiModelProperty(example = "123456")
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;

}
