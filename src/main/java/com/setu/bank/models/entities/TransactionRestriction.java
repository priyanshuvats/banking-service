package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionRestrictionType;
import com.setu.bank.models.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
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
