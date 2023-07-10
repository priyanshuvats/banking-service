package com.setu.bank.controllers;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.setu.bank.constants.AppConstants;
import com.setu.bank.exceptions.BaseException;
import com.setu.bank.models.entities.Account;
import com.setu.bank.models.entities.enums.KycStatus;
import com.setu.bank.models.requests.CreateAccountRequest;
import com.setu.bank.models.requests.UpdateKycStatusRequest;
import com.setu.bank.models.responses.CreateAccountReponse;
import com.setu.bank.models.responses.GetAccountResponse;
import com.setu.bank.models.responses.ResponseType;
import com.setu.bank.models.responses.Status;
import com.setu.bank.services.accounts.AccountService;

import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/bank/accounts")
@AllArgsConstructor
public class AccountController {

	@Autowired
	private final AccountService accountService;

	@GetMapping("/{accountNumber}")
	public ResponseEntity<GetAccountResponse> getAccount(
						@PathVariable @NotEmpty(message = "account can't be empty") 
						@Parameter(example = "123456") String accountNumber){
		try{
			Account account = accountService.getAccount(accountNumber);
			return ResponseEntity.ok(new GetAccountResponse(account.toDto()));
		} catch(BaseException e){
			log.error(e.getMessage() + " Error : " + e);
			GetAccountResponse response = new GetAccountResponse(new Status(ResponseType.ERROR, e.getMessage()));
			return ResponseEntity.status(e.status).body(response);	
		} catch (Exception e){
			log.error(e.getMessage() + " Error : " + e);
			GetAccountResponse response = new GetAccountResponse(new Status(ResponseType.ERROR, AppConstants.DEFAULT_ERROR));
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);	
		}
	}

    @PostMapping("/")
	public ResponseEntity<CreateAccountReponse> createAccount(
			@Valid @RequestBody CreateAccountRequest createAccountRequest) {
		try{
			Account account = accountService.createAccount(createAccountRequest);
			return ResponseEntity.ok(new CreateAccountReponse(account.toDto()));
		} catch (Exception e){
			log.error(e.getMessage() + " Error : " + e);
			CreateAccountReponse response = new CreateAccountReponse(new Status(ResponseType.ERROR, AppConstants.DEFAULT_ERROR));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}
    
	@PutMapping("/{accountNumber}/kyc")
	public ResponseEntity<KycStatus> updateKycStatus(@PathVariable @NotEmpty(message = "account can't be empty") 
													@Parameter(example = "123456") String accountNumber,
													@Valid @RequestBody UpdateKycStatusRequest request) {
		try{
			accountService.updateKycStatus(accountNumber, request.getKycStatus());
			return ResponseEntity.ok(request.getKycStatus());
		} catch(BaseException e){
			log.error(e.getMessage() + " Error : " + e);
			return ResponseEntity.status(e.status).body(null);	
		} catch (Exception e) {
			log.error(e.getMessage() + " Error : " + e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

}
