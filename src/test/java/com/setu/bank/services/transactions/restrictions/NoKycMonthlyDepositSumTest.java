package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.transactions.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class NoKycMonthlyDepositSumTest {

    private TransactionService transactionService;
    private TransactionRestriction transactionRestriction;
    private Account account;
    private NoKycMonthlyDepositSum noKycMonthlyDepositSum;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        transactionRestriction = new TransactionRestriction();
        account = new Account();
        noKycMonthlyDepositSum = new NoKycMonthlyDepositSum(transactionService, transactionRestriction, account);
    }

    @Test
    void runValidation_DepositTransactionWithPendingKycBelowAllowedSum_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(200.0);

        account.setKycStatus(KycStatus.PENDING);
        transactionRestriction.setValue(1000.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        when(transactionService.getLastXDaysTransactionSum(anyString(), any(TransactionType.class), anyInt())).thenReturn(800.0);

        
        RestrictionAction result = noKycMonthlyDepositSum.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
        verify(transactionService).getLastXDaysTransactionSum(anyString(), any(TransactionType.class), anyInt());
    }


    @Test
    void runValidation_DepositTransactionWithPendingKycAboveAllowedSum_ReturnsRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(200.0);

        account.setKycStatus(KycStatus.PENDING);
        transactionRestriction.setValue(1000.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        when(transactionService.getLastXDaysTransactionSum(anyString(), any(TransactionType.class), anyInt())).thenReturn(900.0);

        
        RestrictionAction result = noKycMonthlyDepositSum.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.BLOCK, result.getActionType());
        assertEquals(0.0, result.getCharge());
        verify(transactionService).getLastXDaysTransactionSum(anyString(), any(TransactionType.class), anyInt());
    }

    @Test
    void runValidation_DepositTransactionWithCompletedKycAboveAllowedSum_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(200.0);

        account.setKycStatus(KycStatus.COMPLETED);
        transactionRestriction.setValue(1000.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 0.0));

        when(transactionService.getLastXDaysTransactionSum(anyString(), any(TransactionType.class), anyInt())).thenReturn(900.0);

        
        RestrictionAction result = noKycMonthlyDepositSum.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
    }

    @Test
    void runValidation_NonDepositTransaction_ReturnsAllowRestrictionAction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.WITHDRAWAL);
        request.setAmount(200.0);

        account.setKycStatus(KycStatus.PENDING);
        transactionRestriction.setValue(1000.0);
        transactionRestriction.setAction(new RestrictionAction(RestrictionActionType.BLOCK, 10.0));

        
        RestrictionAction result = noKycMonthlyDepositSum.runValidation(request);

        
        assertNotNull(result);
        assertEquals(RestrictionActionType.ALLOW, result.getActionType());
        assertEquals(0.0, result.getCharge());
        verifyNoInteractions(transactionService);
    }

}

