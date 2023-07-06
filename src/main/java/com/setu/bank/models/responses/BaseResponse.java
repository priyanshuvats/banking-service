package com.setu.bank.models.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public abstract class BaseResponse implements Serializable {
    private static final long serialVersionUID = 766737479775870601L;
    private Status status;

    public BaseResponse(Status status) {
        this.status = status;
    }
}