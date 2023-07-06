package com.setu.bank.models.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTransactionsRequest {
    private String accountNumber;
    private int limit;
    private int offset;
}
