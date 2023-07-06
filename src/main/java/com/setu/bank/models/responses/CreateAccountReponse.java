package com.setu.bank.models.responses;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.setu.bank.models.entities.Account;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonRootName(value = "createAccountResponse")
public class CreateAccountReponse extends BaseResponse{

    private Account account;

    public CreateAccountReponse(Status status) {
        super(status);
    }

    public CreateAccountReponse(Account account){
        this.account = account;
    }
}
