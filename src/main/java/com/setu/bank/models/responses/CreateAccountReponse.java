package com.setu.bank.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.setu.bank.models.dtos.AccountDto;

import lombok.Getter;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "createAccountResponse")
@Getter
public class CreateAccountReponse extends BaseResponse{

    private AccountDto account;

    public CreateAccountReponse(Status status) {
        super(status);
    }

    public CreateAccountReponse(AccountDto account){
        this.account = account;
    }
}
