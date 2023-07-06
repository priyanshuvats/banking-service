package com.setu.bank.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Check;

import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.KycStatus;

import lombok.Builder;
import lombok.Getter;

@Entity
@Table(name = "account")
@Builder
@Getter
public class Account extends BaseEntity{
    @Column(name = "account_number", unique = true)
    private String accountNumber;

    @Column(name = "account_type")
    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @Column
    @Check(constraints = "balance >= 0")
    private Double balance;

    @Column(name = "kyc_status")
    @Enumerated(EnumType.STRING)
    private KycStatus kycStatus;

    @OneToOne
    private User accountOwner;

    public Account toResponseDto(){
        Account account = Account.builder()
                            .accountNumber(this.accountNumber)
                            .accountType(this.accountType)
                            .balance(this.balance)
                            .kycStatus(this.kycStatus)
                            .build();
        return account;
    }
}
