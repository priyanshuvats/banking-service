package com.setu.bank.services.transactions.restrictions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.TransactionRestrictionType;
import com.setu.bank.services.transactions.TransactionService;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class RestrictionServiceFactory {
    
    @Autowired
    private final TransactionService transactionService;

    public IRestrictionService getRestrictionService(TransactionRestriction txnRestriction,
                                            Account account) throws Exception {
        TransactionRestrictionType type = txnRestriction.getType();
        if(TransactionRestrictionType.MINIMUM_BALANCE.equals(type)){
            return new MinBalance(txnRestriction, account);
        } else if(TransactionRestrictionType.MONTHLY_WITHDRAWAL_COUNT.equals(type)){
            return new MonthlyWithdrawalCount(transactionService, txnRestriction);
        } else if(TransactionRestrictionType.SINGLE_DEPOSIT_AMOUNT.equals(type)){
            return new SingleDepositAmount(txnRestriction);
        } else if(TransactionRestrictionType.NO_KYC_MONTHLY_DEPOSIT_SUM.equals(type)){
            return new NoKycMonthlyDepositSum(transactionService, txnRestriction, account);
        } else {
            throw new Exception("Invalid Restriction Type !!");
        }          
    }

}
