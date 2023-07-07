package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class SingleDepositAmountTest {

    private TransactionRestriction transactionRestriction;
    private SingleDepositAmount singleDepositAmount;

    @BeforeEach
    void setUp() {
        transactionRestriction = new TransactionRestriction();
        singleDepositAmount = new SingleDepositAmount(transactionRestriction);
    }

    @Test
    void runValidation_DepositAmountBelowAllowedMaxDeposit_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(100.0);

        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        
        RestrictionAction result = singleDepositAmount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

    @Test
    void runValidation_DepositAmountAboveAllowedMaxDeposit_ReturnsRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(600.0);

        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        
        RestrictionAction result = singleDepositAmount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.BLOCK, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

    @Test
    void runValidation_NonDepositTransaction_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setTransactionType(TransactionType.WITHDRAWAL);
        request.setAmount(100.0);

        transactionRestriction.setValue(500.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 10.0));

        
        RestrictionAction result = singleDepositAmount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

}

