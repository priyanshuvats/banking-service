package com.setu.bank.models.responses;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public abstract class BaseResponse implements Serializable {

    private Status status;

    public BaseResponse(Status status) {
        this.status = status;
    }
}