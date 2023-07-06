package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MinBalance implements IRestrictionService {

    private final TransactionRestriction transactionRestriction;
    private final Account account;

    @Override
    public RestrictionAction runValidation(CreateTransactionRequest createTransactionRequest) {
        Double balance = account.getBalance();
        TransactionType txnType = createTransactionRequest.getTransactionType();
        Double txnAmount = createTransactionRequest.getAmount();
        Double minBalanceNeeded = transactionRestriction.getValue();
        if(txnType.equals(TransactionType.WITHDRAWL) && (balance-txnAmount<minBalanceNeeded)){
            return transactionRestriction.getAction();
        }
        return new RestrictionAction(RestrictionActionType.ALLOW, 0D);
    }
    
}
