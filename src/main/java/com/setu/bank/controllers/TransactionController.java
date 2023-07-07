package com.setu.bank.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.setu.bank.models.dtos.TransactionDto;
import com.setu.bank.models.entities.Transaction;
import com.setu.bank.models.requests.CreateTransactionRequest;
import com.setu.bank.models.requests.GetTransactionsRequest;
import com.setu.bank.models.responses.CreateTransactionResponse;
import com.setu.bank.models.responses.GetTransactionResponse;
import com.setu.bank.models.responses.ResponseType;
import com.setu.bank.models.responses.Status;
import com.setu.bank.services.transactions.TransactionFacade;
import com.setu.bank.services.transactions.TransactionService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/v1/bank/transactions")
@AllArgsConstructor
public class TransactionController {
    
    @Autowired
    private final TransactionService transactionService;
    @Autowired
    private final TransactionFacade transactionFacade;

    @PostMapping("/")
	public ResponseEntity<CreateTransactionResponse> createTransaction(
			@RequestBody CreateTransactionRequest createTransactionRequest) {
		try{
			Transaction txn = transactionFacade.validateAndCreateTransaction(createTransactionRequest);
			return ResponseEntity.ok(new CreateTransactionResponse(txn.toDto()));
		} catch (Exception e){
			log.error(e.getMessage() + " Error : " + e);
			CreateTransactionResponse response = new CreateTransactionResponse(new Status(ResponseType.ERROR, e.getMessage()));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
		}
	}

    @GetMapping("/")
    public ResponseEntity<GetTransactionResponse> getTransactions(@RequestParam(name = "accountNumber", required = true) String accountNumber,
                                                  @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                  @RequestParam(name = "offset", defaultValue = "0") int offset){
        try{
            GetTransactionsRequest request = GetTransactionsRequest.builder()
                                                        .accountNumber(accountNumber)
                                                        .limit(limit)
                                                        .offset(offset)
                                                        .build();
            List<Transaction> txns = transactionService.getTransactions(request);
            List<TransactionDto> responsedDtos = new ArrayList<>();
            for(Transaction txn: txns){
                responsedDtos.add(txn.toDto());
            }
            return ResponseEntity.ok(new GetTransactionResponse(responsedDtos));
        } catch (Exception e){
            log.error(e.getMessage() + " Error : " + e);
			GetTransactionResponse response = new GetTransactionResponse(new Status(ResponseType.ERROR, e.getMessage()));
			return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
        }
    }
}
