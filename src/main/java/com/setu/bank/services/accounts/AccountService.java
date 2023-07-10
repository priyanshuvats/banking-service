package com.setu.bank.services.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.setu.bank.exceptions.AccountNotFoundException;
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

    public Account getAccount(String accountNumber) throws AccountNotFoundException{
        Account account = accountRepository.findByAccountNumber(accountNumber);
        if(account == null){
            throw new AccountNotFoundException("Account doesn't exist");
        }
        return account;
    }

    public void deposit(String accountNumber, Double amount) throws AccountNotFoundException{
        int affectedRows = accountRepository.incrementBalance(accountNumber, amount);
        if (affectedRows == 0) {
            throw new AccountNotFoundException("Account not found");
        }
    }

    public void withdraw(String accountNumber, Double amount) throws AccountNotFoundException{
        int affectedRows =  accountRepository.decrementBalance(accountNumber, amount);
        if (affectedRows == 0) {
            throw new AccountNotFoundException("Account not found");
        }
    }

    public void updateKycStatus(String accountNumber, KycStatus status) throws AccountNotFoundException{
        int affectedRows = accountRepository.updateKyc(accountNumber, status);
        if (affectedRows == 0) {
            throw new AccountNotFoundException("Account not found");
        }
    }
    
}
