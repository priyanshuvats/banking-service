package com.setu.bank.models.entities;

import java.io.Serializable;

import com.setu.bank.models.entities.enums.RestrictionActionType;

import lombok.Data;

@Data
public class RestrictionAction implements Serializable{
    private RestrictionActionType actionType;
    private Double charge;
}
