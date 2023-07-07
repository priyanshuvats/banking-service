package com.setu.bank.services.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.requests.CreateAccountRequest;
import com.setu.bank.repositories.AccountRepository;
import com.setu.bank.repositories.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AccountService {

    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final UserRepository userRepository;
    
    // TODO: Implement a check on opening balance by leveraging
    // openingBalance as a deposit transaction
    public Account createAccount(CreateAccountRequest createAccountRequest){
        Account account = Account.builder()
                            .accountNumber(createAccountRequest.getAccountNumber())
                            .accountOwner(userRepository.getReferenceById(
                                createAccountRequest.getUserId()))
                            .accountType(createAccountRequest.getAccountType())
                            .balance(createAccountRequest.getOpeningBalance())
                            .kycStatus(KycStatus.PENDING)
                            .build();
        return accountRepository.save(account);
    }

    public Account getAccount(String accountNumber){
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void deposit(String accountNumber, Double amount){
        accountRepository.incrementBalance(accountNumber, amount);
    }

    public void withdraw(String accountNumber, Double amount){
        accountRepository.decrementBalance(accountNumber, amount);
    }

    public void updateKycStatus(String accountNumber, KycStatus status){
        accountRepository.updateKyc(accountNumber, status);
    }
    
}
