package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionRestrictionType;
import com.setu.bank.models.entities.enums.TransactionType;

@Entity
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
    // @Type(type = "json")
    private RestrictionAction action;

}
