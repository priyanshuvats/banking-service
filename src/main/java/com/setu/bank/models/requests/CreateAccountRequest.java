package com.setu.bank.models.requests;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.setu.bank.models.entities.enums.AccountType;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CreateAccountRequest {
    @ApiModelProperty(example = "123456")
    @NotEmpty(message = "Account number must not be empty")
    private String accountNumber;
    @NotNull(message = "Please select REGULAR_SAVINGS/ZERO_BALANCE/STUDENT as account type")
    private AccountType accountType;
    @ApiModelProperty(example = "1")
    @Min(value = 1, message = "userId should be between 1 and 5 both inclusive - pre configured")
    @Max(value = 5, message = "userId should be between 1 and 5 both inclusive - pre configured")
    private Long userId;
    @ApiModelProperty(example = "1000")
    @Positive(message = "Opening balance must be positive")
    private Double openingBalance;
}
