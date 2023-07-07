package com.setu.bank.models.dtos;

import java.io.Serializable;

import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.KycStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AccountDto implements Serializable{
    private String accountNumber;
    private AccountType accountType;
    private Double balance;
    private KycStatus kycStatus;
}
