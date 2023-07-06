package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.responses.Status;

import lombok.Builder;

@Entity
@Builder
public class Transaction extends BaseEntity{

    @Column
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Column
    private Double amount;

    @Column
    private Double charge;

    @ManyToOne
    private Account account;

    public Status toResponseDto() {
        return null;
    }
}
