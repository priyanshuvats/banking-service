package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionRestrictionType;
import com.setu.bank.models.entities.enums.TransactionType;

import lombok.Getter;

@Entity
@Getter
public class TransactionRestriction extends BaseEntity{
    
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Enumerated(EnumType.STRING)
    private TransactionRestrictionType type;

    @Column
    private Double value;

    @Column(name = "action", columnDefinition = "json")
    private RestrictionAction action;

}
