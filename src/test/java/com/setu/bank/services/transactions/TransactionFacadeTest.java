package com.setu.bank.services.transactions;

import com.setu.bank.exceptions.InvalidTransactionException;
import com.setu.bank.models.entities.*;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionRestrictionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.accounts.AccountService;
import com.setu.bank.services.transactions.restrictions.IRestrictionService;
import com.setu.bank.services.transactions.restrictions.RestrictionServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionFacadeTest {

    @Mock
    private AccountService accountService;
    @Mock
    private TransactionService transactionService;
    @Mock
    private RestrictionServiceFactory restrictionServiceFactory;

    private TransactionFacade transactionFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        transactionFacade = new TransactionFacade(accountService, transactionService, restrictionServiceFactory);
    }

    @Test
    void validateAndCreateTransaction_ValidTransaction_ReturnsCreatedTransaction() throws Exception {
        // Arrange
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setAccountType(AccountType.REGULAR_SAVINGS);

        List<TransactionRestriction> restrictions = Collections.singletonList(
                TransactionRestriction.builder()
                            .accountType(AccountType.REGULAR_SAVINGS)
                            .transactionType(TransactionType.DEPOSIT)
                            .type(TransactionRestrictionType.SINGLE_DEPOSIT_AMOUNT)
                            .value(50.0)
                            .action(new RestrictionAction(RestrictionActionType.ALLOW, 5.0))
                            .build());


        RestrictionAction restrictionAction = new RestrictionAction(RestrictionActionType.ALLOW, 5.0);
        // restrictionAction.setActionType(RestrictionActionType.ALLOW);

        when(accountService.getAccount(request.getAccountNumber())).thenReturn(account);
        when(transactionService.getRestrictions(request.getTransactionType(), account.getAccountType())).thenReturn(restrictions);
        when(restrictionServiceFactory.getRestrictionService(any(), any())).thenReturn(mock(IRestrictionService.class));
        when(restrictionServiceFactory.getRestrictionService(any(), any()).runValidation(request)).thenReturn(restrictionAction);
        when(transactionService.createTransaction(request, account, 5.0)).thenReturn(new Transaction());

        // Act
        Transaction createdTransaction = transactionFacade.validateAndCreateTransaction(request);

        // Assert
        assertNotNull(createdTransaction);
        verify(accountService).getAccount(request.getAccountNumber());
        verify(transactionService).getRestrictions(request.getTransactionType(), account.getAccountType());
        // verify(restrictionServiceFactory).getRestrictionService(any(), any());
        verify(restrictionServiceFactory.getRestrictionService(any(), any())).runValidation(request);
        verify(transactionService).createTransaction(request, account, 5.0);
    }

    @Test
    void validateAndCreateTransaction_InvalidTransaction_ThrowsInvalidTransactionException() throws Exception {
        // Arrange
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAccountNumber("123456789");
        request.setTransactionType(TransactionType.DEPOSIT);
        request.setAmount(100.0);

        Account account = new Account();
        account.setAccountNumber("123456789");
        account.setAccountType(AccountType.REGULAR_SAVINGS);

        List<TransactionRestriction> restrictions = Collections.singletonList(
                TransactionRestriction.builder()
                            .accountType(AccountType.REGULAR_SAVINGS)
                            .transactionType(TransactionType.DEPOSIT)
                            .type(TransactionRestrictionType.SINGLE_DEPOSIT_AMOUNT)
                            .value(50.0)
                            .action(new RestrictionAction(RestrictionActionType.BLOCK, 0.0))
                            .build());


        RestrictionAction restrictionAction = new RestrictionAction(RestrictionActionType.BLOCK, 0.0);

        when(accountService.getAccount(request.getAccountNumber())).thenReturn(account);
        when(transactionService.getRestrictions(request.getTransactionType(), account.getAccountType())).thenReturn(restrictions);
        when(restrictionServiceFactory.getRestrictionService(any(), any())).thenReturn(mock(IRestrictionService.class));
        when(restrictionServiceFactory.getRestrictionService(any(), any()).runValidation(request)).thenReturn(restrictionAction);

        // Act & Assert
        assertThrows(InvalidTransactionException.class, () -> transactionFacade.validateAndCreateTransaction(request));
        verify(accountService).getAccount(request.getAccountNumber());
        verify(transactionService).getRestrictions(request.getTransactionType(), account.getAccountType());
        // verify(restrictionServiceFactory).getRestrictionService(any(), any());
        verify(restrictionServiceFactory.getRestrictionService(any(), any())).runValidation(request);
        verify(transactionService, never()).createTransaction(any(), any(), anyDouble());
    }

    // Add more test cases as needed

}
