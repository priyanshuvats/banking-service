package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.transactions.TransactionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MonthlyWithdrawalCount implements IRestrictionService{

    private final TransactionService transactionService;
    private final TransactionRestriction transactionRestriction;

    @Override
    public RestrictionAction runValidation(CreateTransactionRequest createTransactionRequest) {
        String accountNumber = createTransactionRequest.getAccountNumber();
        TransactionType txnType = createTransactionRequest.getTransactionType();
        if(txnType.equals(TransactionType.WITHDRAWAL)){
            Long lastThirtyDaysWithdrawlTxn = transactionService.getLastXDaysTransactionCount(
                                            accountNumber, txnType, 30);
            Double limit = transactionRestriction.getValue();
            if(lastThirtyDaysWithdrawlTxn>=limit){
                return transactionRestriction.getAction();
            }
        }
        return new RestrictionAction(RestrictionActionType.ALLOW, 0D);
    }
    
}
