package com.setu.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.enums.KycStatus;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long>{

    Account findByAccountNumber(String accountNumber);
    
    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance - :amount WHERE a.accountNumber = :accountNumber")
    int decrementBalance(@Param("accountNumber") String accountNumber, @Param("amount") Double amount);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.balance = a.balance + :amount WHERE a.accountNumber = :accountNumber")
    int incrementBalance(@Param("accountNumber") String accountNumber, @Param("amount") Double amount);

    @Transactional
    @Modifying
    @Query("UPDATE Account a SET a.kycStatus = :kycStatus WHERE a.accountNumber = :accountNumber")
    int updateKyc(@Param("accountNumber") String accountNumber, @Param("kycStatus") KycStatus kycStatus);

}
