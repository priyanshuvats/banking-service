package com.setu.bank.services.transactions;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.models.requests.GetTransactionsRequest;
import com.setu.bank.repositories.TransactionRepository;
import com.setu.bank.repositories.TransactionRestrictionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Only happy flows are tested due to lack of time
class TransactionServiceTest {

    private TransactionRepository transactionRepository;
    private TransactionRestrictionRepository transactionRestrictionRepository;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionRepository = mock(TransactionRepository.class);
        transactionRestrictionRepository = mock(TransactionRestrictionRepository.class);
        transactionService = new TransactionService(transactionRepository, transactionRestrictionRepository);
    }

    @Test
    void createTransaction_ValidRequest_ReturnsCreatedTransaction() {
        
        CreateTransactionRequest request = new CreateTransactionRequest();
        request.setAmount(100.0);
        request.setTransactionType(TransactionType.DEPOSIT);

        Account account = new Account();
        account.setAccountNumber("123456789");

        Transaction createdTransaction = new Transaction();

        when(transactionRepository.save(any(Transaction.class))).thenReturn(createdTransaction);

        
        Transaction result = transactionService.createTransaction(request, account, 0.0);

        
        assertNotNull(result);
        assertSame(createdTransaction, result);
        verify(transactionRepository).save(any(Transaction.class));
    }

    @Test
    void getTransactions_ValidRequest_ReturnsTransactionList() {
        
        GetTransactionsRequest request = new GetTransactionsRequest();
        request.setAccountNumber("123456789");
        request.setLimit(10);
        request.setOffset(0);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction());
        transactions.add(new Transaction());

        Pageable pageable = PageRequest.of(0, 10);

        when(transactionRepository.findAllByAccountNumber(anyString(), any(Pageable.class))).thenReturn(transactions);

        
        List<Transaction> result = transactionService.getTransactions(request);

        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRepository).findAllByAccountNumber(anyString(), any(Pageable.class));
    }

    @Test
    void getLastXDaysTransactionCount_ValidRequest_ReturnsTransactionCount() {
        
        String accountNumber = "123456789";
        TransactionType txnType = TransactionType.DEPOSIT;
        int numOfDays = 30;
        Long transactionCount = 10L;

        when(transactionRepository.countTransactionWithinLastXDays(anyString(), any(TransactionType.class), anyInt())).thenReturn(transactionCount);

        
        Long result = transactionService.getLastXDaysTransactionCount(accountNumber, txnType, numOfDays);

        
        assertNotNull(result);
        assertEquals(transactionCount, result);
        verify(transactionRepository).countTransactionWithinLastXDays(anyString(), any(TransactionType.class), anyInt());
    }

    @Test
    void getLastXDaysTransactionSum_ValidRequest_ReturnsTransactionSum() {
        
        String accountNumber = "123456789";
        TransactionType txnType = TransactionType.DEPOSIT;
        int numOfDays = 30;
        Double transactionSum = 500.0;

        when(transactionRepository.sumTransactionWithinLastXDays(anyString(), any(TransactionType.class), anyInt())).thenReturn(transactionSum);

        
        Double result = transactionService.getLastXDaysTransactionSum(accountNumber, txnType, numOfDays);

        
        assertNotNull(result);
        assertEquals(transactionSum, result);
        verify(transactionRepository).sumTransactionWithinLastXDays(anyString(), any(TransactionType.class), anyInt());
    }

    @Test
    void getRestrictions_ValidRequest_ReturnsTransactionRestrictions() {
        
        TransactionType txnType = TransactionType.DEPOSIT;
        AccountType acType = AccountType.REGULAR_SAVINGS;

        List<TransactionRestriction> restrictions = new ArrayList<>();
        restrictions.add(new TransactionRestriction());
        restrictions.add(new TransactionRestriction());

        when(transactionRestrictionRepository.findAllByTransactionTypeAndAccountType(any(TransactionType.class), any(AccountType.class))).thenReturn(restrictions);

        
        List<TransactionRestriction> result = transactionService.getRestrictions(txnType, acType);

        
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(transactionRestrictionRepository).findAllByTransactionTypeAndAccountType(any(TransactionType.class), any(AccountType.class));
    }


}

