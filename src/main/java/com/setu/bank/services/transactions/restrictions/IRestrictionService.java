package com.setu.bank.services.transactions.restrictions;

import com.setu.bank.models.entities.RestrictionAction;
import com.setu.bank.models.requests.CreateTransactionRequest;

public interface IRestrictionService {
    
    public RestrictionAction runValidation(CreateTransactionRequest createTransactionRequest);

}
