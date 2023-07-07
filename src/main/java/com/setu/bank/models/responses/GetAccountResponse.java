package com.setu.bank.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.setu.bank.models.dtos.AccountDto;

import lombok.Getter;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "getAccountResponse")
@Getter
public class GetAccountResponse extends BaseResponse{
    private AccountDto account;

    public GetAccountResponse(Status status) {
        super(status);
    }

    public GetAccountResponse(AccountDto account){
        this.account = account;
    }
}
