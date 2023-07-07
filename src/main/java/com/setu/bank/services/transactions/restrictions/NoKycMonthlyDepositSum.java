package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.constants.AppConstants;
import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.entities.TransactionRestriction;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.entities.enums.RestrictionActionType;
import com.setu.bank.models.entities.enums.TransactionType;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.services.transactions.TransactionService;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoKycMonthlyDepositSum implements IRestrictionService{

    private final TransactionService transactionService;
    private final TransactionRestriction transactionRestriction;
    private final Account account;

    @Override
    public RestrictionAction runValidation(CreateTransactionRequest createTransactionRequest) {
        TransactionType txnType = createTransactionRequest.getTransactionType();
        KycStatus kycStatus = account.getKycStatus();
        if(txnType.equals(TransactionType.DEPOSIT) && kycStatus.equals(KycStatus.PENDING)){
            Double lastThirtyDaysDepositAmount = transactionService.getLastXDaysTransactionSum(
                createTransactionRequest.getAccountNumber(), txnType, AppConstants.MONTH_DAY_COUNT);
            Double allowedLimit = transactionRestriction.getValue();
            Double txnAmount = createTransactionRequest.getAmount();
            if(lastThirtyDaysDepositAmount + txnAmount > allowedLimit){
                return transactionRestriction.getAction();
            }
        }
        return new RestrictionAction(RestrictionActionType.ALLOW, AppConstants.ZERO_CHARGE);
    }
    
}
