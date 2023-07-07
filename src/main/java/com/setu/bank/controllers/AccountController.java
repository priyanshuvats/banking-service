package com.setu.bank.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.requests.CreateAccountRequest;
import com.setu.bank.models.requests.UpdateKycStatusRequest;
import com.setu.bank.models.responses.CreateAccountReponse;
import com.setu.bank.models.responses.ResponseType;
import com.setu.bank.models.responses.Status;
import com.setu.bank.services.accounts.AccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/bank/accounts")
@AllArgsConstructor
public class AccountController {

	@Autowired
	private final AccountService accountService;

    @PostMapping("/")
	public ResponseEntity<CreateAccountReponse> createAccount(
			@RequestBody CreateAccountRequest createAccountRequest) {
		try{
			Account account = accountService.createAccount(createAccountRequest);
			return ResponseEntity.ok(new CreateAccountReponse(account.toDto()));
		} catch (Exception e){
			log.error(e.getMessage() + " Error : " + e);
			CreateAccountReponse response = new CreateAccountReponse(new Status(ResponseType.ERROR, e.getMessage()));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}
    
	@PutMapping("/{accountNumber}/kyc")
	public ResponseEntity<KycStatus> updateKycStatus(@PathVariable String accountNumber,
								@RequestBody UpdateKycStatusRequest request) {
		try{
			KycStatus status = accountService.updateKycStatus(accountNumber, request.getKycStatus());
			return ResponseEntity.ok(status);
		} catch (Exception e) {
			log.error(e.getMessage() + " Error : " + e);
			return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
		}
	}

}
