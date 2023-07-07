package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.constants.AppConstants;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SingleDepositAmount implements IRestrictionService{

    private TransactionRestriction transactionRestriction;

    @Override
    public RestrictionAction runValidation(CreateTransactionRequest createTransactionRequest) {
        Double allowedMaxDeposit = transactionRestriction.getValue();
        Double txnAmount = createTransactionRequest.getAmount();
        TransactionType txnType = createTransactionRequest.getTransactionType();
        if(txnType.equals(TransactionType.DEPOSIT) && (txnAmount>allowedMaxDeposit)){
            return transactionRestriction.getAction();
        }
        return new RestrictionAction(RestrictionActionType.ALLOW, AppConstants.ZERO);
    }
    
}
