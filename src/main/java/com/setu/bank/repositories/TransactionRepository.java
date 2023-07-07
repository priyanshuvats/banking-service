package com.setu.bank.repositories;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.entities.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    @Query("""
        SELECT count(t) FROM Transaction t JOIN t.account a WHERE a.accountNumber = :accountNumber and 
        t.transactionType = :transactionType and
        date(t.createdAt) between (CURRENT_DATE() - :numOfDays) AND CURRENT_DATE()
        """)
    Long countTransactionWithinLastXDays(
        @Param("accountNumber") String accountNumber,
        @Param("transactionType") TransactionType transactionType,
        @Param("numOfDays") int numOfDays);

    

    @Query("""
        SELECT sum(t.amount) FROM Transaction t JOIN t.account a WHERE a.accountNumber = :accountNumber and 
        t.transactionType = :transactionType and
        date(t.createdAt) between (CURRENT_DATE() - :numOfDays) AND CURRENT_DATE()
        """)
    Double sumTransactionWithinLastXDays(
        @Param("accountNumber") String accountNumber,
        @Param("transactionType") TransactionType transactionType,
        @Param("numOfDays") int numOfDays);

    
    @Query("""
        SELECT t FROM Transaction t JOIN t.account a WHERE a.accountNumber = :accountNumber
        """)
    List<Transaction> findAllByAccountNumber(
                    @Param("accountNumber") String accountNumber,
                    Pageable pageable);

}
