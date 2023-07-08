package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.AccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @ApiModelProperty(example = "123456")
    private String accountNumber;
    private AccountType accountType;
    @ApiModelProperty(example = "1")
    private Long userId;
    @ApiModelProperty(example = "1000")
    private Double openingBalance;
}
