package com.setu.bank.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "user")
public class User extends BaseEntity{
    
    @Column
    private String name;

    @Column(unique = true)
    private String email;
}
