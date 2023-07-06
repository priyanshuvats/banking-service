package com.setu.bank.services.transactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.models.requests.GetTransactionsRequest;
import com.setu.bank.repositories.TransactionRepository;
import com.setu.bank.repositories.TransactionRestrictionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TransactionService {
    
    @Autowired
    private final TransactionRepository transactionRepository;
    @Autowired
    private final TransactionRestrictionRepository transactionRestrictionRepository;

    public Transaction createTransaction(
        CreateTransactionRequest createTransactionsRequest, 
        Account account,
        Double charge){
        Transaction txn = Transaction.builder()
                                    .account(account)
                                    .amount(createTransactionsRequest.getAmount())
                                    .charge(charge)
                                    .transactionType(createTransactionsRequest.getTransactionType())
                                    .build();
        return transactionRepository.save(txn);
    }

    public List<Transaction> getTransactions(GetTransactionsRequest getTransactionsRequest){
        return null;
    }

    public Long getLastXDaysTransactionCount(String accountNumber, TransactionType txnType, int numOfDays){
        return transactionRepository.countTransactionWithinLastXDays(accountNumber, txnType, numOfDays);
    }

    public Double getLastXDaysTransactionSum(String accountNumber, TransactionType txnType, int numOfDays){
        return transactionRepository.sumTransactionWithinLastXDays(accountNumber, txnType, numOfDays);
    }

    public List<TransactionRestriction> getRestrictions(TransactionType txnType, AccountType acType){
        return transactionRestrictionRepository.findAllByTransactionTypeAndAccountType(txnType, acType);
    }

}
