package com.setu.bank.models.entities;

import javax.persistence.*;

import com.setu.bank.models.dtos.TransactionDto;
import com.setu.bank.models.entities.enums.TransactionType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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

    public TransactionDto toDto() {
        return TransactionDto.builder()
                            .accountNumber(this.account.getAccountNumber())
                            .transactionType(this.transactionType)
                            .amount(this.amount)
                            .charge(this.charge)
                            .transactedAt(super.getCreatedAt())
                            .build();
    }
}
