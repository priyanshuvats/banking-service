package com.setu.bank.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.setu.bank.models.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    
}
