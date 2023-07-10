package com.setu.bank.services.accounts;

import com.setu.bank.exceptions.AccountNotFoundException;
import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.User;
import com.setu.bank.models.entities.enums.AccountType;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.requests.CreateAccountRequest;
import com.setu.bank.repositories.AccountRepository;
import com.setu.bank.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;
    @Mock
    private UserRepository userRepository;

    private AccountService accountService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        accountService = new AccountService(accountRepository, userRepository);
    }

    @Test
    void createAccount_ValidRequest_ReturnsCreatedAccount() {
        
        CreateAccountRequest request = new CreateAccountRequest();
        request.setAccountNumber("123456789");
        request.setUserId(1L);
        request.setAccountType(AccountType.REGULAR_SAVINGS);
        request.setOpeningBalance(100.0);

        Account createdAccount = new Account();
        createdAccount.setAccountNumber("123456789");
        createdAccount.setAccountType(AccountType.REGULAR_SAVINGS);

        when(userRepository.getReferenceById(eq(request.getUserId()))).thenReturn(new User());
        when(accountRepository.save(any(Account.class))).thenReturn(createdAccount);

        
        Account result = accountService.createAccount(request);

        
        assertNotNull(result);
        assertEquals("123456789", result.getAccountNumber());
        assertEquals(AccountType.REGULAR_SAVINGS, result.getAccountType());
        verify(userRepository).getReferenceById(any(Long.class));
        verify(accountRepository).save(any(Account.class));
    }

    @Test
    void getAccount_ExistingAccountNumber_ReturnsAccount() throws AccountNotFoundException {
        
        String accountNumber = "123456789";
        Account existingAccount = new Account();
        existingAccount.setAccountNumber(accountNumber);

        when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(existingAccount);

        
        Account result = accountService.getAccount(accountNumber);

        
        assertNotNull(result);
        assertEquals(accountNumber, result.getAccountNumber());
        verify(accountRepository).findByAccountNumber(accountNumber);
    }

    @Test
    void deposit_ValidAccountNumberAndAmount_CallsIncrementBalance() throws AccountNotFoundException {
        
        String accountNumber = "123456789";
        Double amount = 100.0;

        when(accountRepository.incrementBalance(eq(accountNumber), eq(amount))).thenReturn(1);    
        accountService.deposit(accountNumber, amount);

        
        verify(accountRepository).incrementBalance(accountNumber, amount);
    }

    @Test
    void withdraw_ValidAccountNumberAndAmount_CallsDecrementBalance() throws AccountNotFoundException {
        
        String accountNumber = "123456789";
        Double amount = 100.0;

        when(accountRepository.decrementBalance(eq(accountNumber), eq(amount))).thenReturn(1);        
        accountService.withdraw(accountNumber, amount);

        
        verify(accountRepository).decrementBalance(accountNumber, amount);
    }

    @Test
    void updateKycStatus_ValidAccountNumberAndStatus_CallsUpdateKyc() throws AccountNotFoundException {

        String accountNumber = "123456789";
        KycStatus status = KycStatus.COMPLETED;

        when(accountRepository.updateKyc(eq(accountNumber), eq(KycStatus.COMPLETED))).thenReturn(1);

        accountService.updateKycStatus(accountNumber, status);

        verify(accountRepository).updateKyc(accountNumber, status);
    }

}

