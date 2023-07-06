package com.setu.bank.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

@Entity
@Table(name = "account")
public class Account extends BaseEntity{
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column
    @Check(constraints = "balance > 0")
    private Double balance;

    @OneToOne
    private User accountOwner;
}
