package com.setu.bank.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Check;

import com.setu.bank.models.dtos.AccountDto;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.KycStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@Setter
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

    public AccountDto toDto(){
        AccountDto accountDto = AccountDto.builder()
                            .accountNumber(this.accountNumber)
                            .accountType(this.accountType)
                            .balance(this.balance)
                            .kycStatus(this.kycStatus)
                            .build();
        return accountDto;
    }

}
