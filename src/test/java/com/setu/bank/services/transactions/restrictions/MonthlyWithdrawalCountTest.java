package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.transactions.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class MonthlyWithdrawalCountTest {

    private TransactionService transactionService;
    private TransactionRestriction transactionRestriction;
    private MonthlyWithdrawalCount monthlyWithdrawalCount;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        transactionRestriction = new TransactionRestriction();
        monthlyWithdrawalCount = new MonthlyWithdrawalCount(transactionService, transactionRestriction);
    }

    @Test
    void runValidation_WithdrawalTransactionBelowLimit_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.WITHDRAWAL);

        when(transactionService.getLastXDaysTransactionCount(anyString(), any(TransactionType.class), anyInt())).thenReturn(5L);

        transactionRestriction.setValue(10.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.ALLOW, 10.0));

        
        RestrictionAction result = monthlyWithdrawalCount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
        verify(transactionService).getLastXDaysTransactionCount(anyString(), any(TransactionType.class), anyInt());
    }

    @Test
    void runValidation_WithdrawalTransactionAboveLimit_ReturnsRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.WITHDRAWAL);

        when(transactionService.getLastXDaysTransactionCount(anyString(), any(TransactionType.class), anyInt())).thenReturn(10L);

        transactionRestriction.setValue(10.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.ALLOW, 10.0));

        
        RestrictionAction result = monthlyWithdrawalCount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(10.0, result.getCharge());
        verify(transactionService).getLastXDaysTransactionCount(anyString(), any(TransactionType.class), anyInt());
    }

    @Test
    void runValidation_NonWithdrawalTransaction_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);

        transactionRestriction.setValue(10.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 10.0));

        
        RestrictionAction result = monthlyWithdrawalCount.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
        verifyNoInteractions(transactionService);
    }

}

