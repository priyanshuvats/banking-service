package com.setu.bank.models.requests;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.setu.bank.models.entities.enums.TransactionType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateTransactionRequest {
    @ApiModelProperty(example = "123456")
    @NotEmpty(message = "account number can't be empty")
    private String accountNumber;
    @NotNull(message = "Please select WITHDRAWAL or DEPOSIT as transaction type")
    private TransactionType transactionType;
    @Positive(message = "amount should be positive")
    private Double amount;

}
