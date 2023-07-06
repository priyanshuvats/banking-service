package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.entities.enums.TransactionType;

@Entity
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
}
