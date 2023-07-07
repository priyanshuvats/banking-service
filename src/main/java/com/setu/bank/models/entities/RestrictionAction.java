package com.setu.bank.models.entities;

import java.io.Serializable;

import com.setu.bank.constants.AppConstants;
import com.setu.bank.models.entities.enums.RestrictionActionType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RestrictionAction implements Serializable{
    private RestrictionActionType actionType;
    private Double charge = AppConstants.ZERO;
}
