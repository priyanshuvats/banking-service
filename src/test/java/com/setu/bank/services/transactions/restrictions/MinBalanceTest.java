package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MinBalanceTest {

    private TransactionRestriction transactionRestriction;
    private Account account;
    private MinBalance minBalance;

    @BeforeEach
    void setUp() {
        transactionRestriction = new TransactionRestriction();
        account = new Account();
        minBalance = new MinBalance(transactionRestriction, account);
    }

    @Test
    void runValidation_WithdrawalTransactionBelowMinBalance_ReturnsRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.WITHDRAWAL);
        request.setAmount(600.0);

        account.setBalance(1000.0);
        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        
        RestrictionAction result = minBalance.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.BLOCK, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

    @Test
    void runValidation_WithdrawalTransactionAboveMinBalance_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.WITHDRAWAL);
        request.setAmount(200.0);

        account.setBalance(1000.0);
        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        
        RestrictionAction result = minBalance.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

    @Test
    void runValidation_DepositTransaction_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(600.0);

        account.setBalance(1000.0);
        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        
        RestrictionAction result = minBalance.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

}

