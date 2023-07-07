package com.setu.bank.models.dtos;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.setu.bank.models.entities.enums.TransactionType;

import lombok.Builder;
import lombok.Getter;


@Builder
@Getter
public class TransactionDto implements Serializable{
    private String accountNumber;
    private TransactionType transactionType;
    private Double amount;
    private Double charge;
    private LocalDateTime transactedAt;
}
