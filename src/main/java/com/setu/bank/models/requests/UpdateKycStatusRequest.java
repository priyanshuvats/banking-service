package com.setu.bank.models.requests;

import javax.validation.constraints.NotNull;

import com.setu.bank.models.entities.enums.KycStatus;

import lombok.Data;

@Data
public class UpdateKycStatusRequest {
    @NotNull(message = "kyc status can't be empty")
    private KycStatus kycStatus;
}
