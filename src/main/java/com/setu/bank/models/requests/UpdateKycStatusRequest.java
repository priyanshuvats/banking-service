package com.setu.bank.models.requests;

import com.setu.bank.models.entities.enums.KycStatus;

import lombok.Data;

@Data
public class UpdateKycStatusRequest {
    private String accountNumber;
    private KycStatus kycStatus;
}
