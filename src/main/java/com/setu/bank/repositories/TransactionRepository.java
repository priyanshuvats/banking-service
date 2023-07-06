package com.setu.bank.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.entities.enums.TransactionType;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long>{

    @Query("""
        SELECT count(t) FROM Transaction t JOIN t.account a aWHERE a.accountNumber = :accountNumber and 
        t.transactionType = :transactionType and
        t.createdAt between NOW() AND NOW()-INTERVAL :numOfDays DAY
        """)
    Long countTransactionWithinLastXDays(
        @Param("accountNumber") String accountNumber,
        @Param("transactionType") TransactionType transactionType,
        @Param("numOfDays") int numOfDays);

    

    @Query("""
        SELECT sum(t) FROM Transaction t JOIN t.account a aWHERE a.accountNumber = :accountNumber and 
        t.transactionType = :transactionType and
        t.createdAt between NOW() AND NOW()-INTERVAL :numOfDays DAY
        """)
    Double sumTransactionWithinLastXDays(
        @Param("accountNumber") String accountNumber,
        @Param("transactionType") TransactionType transactionType,
        @Param("numOfDays") int numOfDays);
    
    // List<Transaction> findAllByAccountNumberAndTransactionTypeCreatedAtBetween(
    //                 String accountNumber, TransactionType txnType,
    //                 LocalDateTime startDate, LocalDateTime endDate);

    
    @Query("""
        SELECT t FROM Transaction t JOIN t.account a aWHERE a.accountNumber = :accountNumber 
        limit :limit offset :offset
        """)
    List<Transaction> findAllByAccountNumber(
                    @Param("accountNumber") String accountNumber,
                    @Param("limit") int limit,
                    @Param("offset") int offset);

}
