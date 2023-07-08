package com.setu.bank.services.transactions;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.setu.bank.constants.AppConstants;
import com.setu.bank.exceptions.InvalidTransactionException;
import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.accounts.AccountService;
import com.setu.bank.services.transactions.restrictions.IRestrictionService;
import com.setu.bank.services.transactions.restrictions.RestrictionServiceFactory;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class TransactionFacade {

    @Autowired
    private final AccountService accountService;
    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final RestrictionServiceFactory restrictionServiceFactory;
    
    @Transactional
    public Transaction validateAndCreateTransaction(CreateTransactionRequest createTransactionsRequest) throws Exception {
        String accountNumber = createTransactionsRequest.getAccountNumber();
        Account account = accountService.getAccount(accountNumber);
        TransactionType txnType = createTransactionsRequest.getTransactionType();
        List<TransactionRestriction> txnRestrictions = transactionService.getRestrictions(txnType, account.getAccountType());
        Double charges = AppConstants.ZERO;

        // run validations - can be optimised by running on multiple threads parallely
        for(TransactionRestriction restriction: txnRestrictions){
            IRestrictionService restrictionService = restrictionServiceFactory.getRestrictionService(restriction, account);
            RestrictionAction restrictionAction = restrictionService.runValidation(createTransactionsRequest);
            RestrictionActionType actionType = restrictionAction.getActionType();
            if(actionType.equals(RestrictionActionType.BLOCK)){
                throw new InvalidTransactionException("Transaction failed some restrictions!!");
            }
            charges += restrictionAction.getCharge()==null ? AppConstants.ZERO : restrictionAction.getCharge();
        }
        // settle balance
        settleAccountBalance(createTransactionsRequest, charges);
        // create transaction
        return transactionService.createTransaction(createTransactionsRequest, account, charges);
    }


    private void settleAccountBalance(CreateTransactionRequest createTransactionsRequest, 
                                    Double charges) throws InvalidTransactionException{
        String accountNumber = createTransactionsRequest.getAccountNumber();
        TransactionType txnType = createTransactionsRequest.getTransactionType();
        Double txnAmount = createTransactionsRequest.getAmount();
        if(txnType.equals(TransactionType.DEPOSIT)){
            accountService.deposit(accountNumber, txnAmount-charges);
        } else if (txnType.equals(TransactionType.WITHDRAWAL)){
            accountService.withdraw(accountNumber, txnAmount+charges);
        } else {
            throw new InvalidTransactionException("Please use DEPOSIT or WITHDRAWAL as transactionType");
        }

    }

}
