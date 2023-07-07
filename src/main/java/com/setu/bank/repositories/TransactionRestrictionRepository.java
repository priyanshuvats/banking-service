package com.setu.bank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionType;

@Repository
public interface TransactionRestrictionRepository extends JpaRepository<TransactionRestriction, Long>{
    
    List<TransactionRestriction> findAllByTransactionTypeAndAccountType(
        TransactionType txnType, AccountType accountType
    );

}
