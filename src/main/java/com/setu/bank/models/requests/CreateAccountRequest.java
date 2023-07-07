package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.AccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateAccountRequest {
    private String accountNumber;
    private AccountType accountType;
    @ApiModelProperty(notes = "Please user userId between 1 to 5 as they are created by default")
    private Long userId;
    private Double openingBalance;
}
