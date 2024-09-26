package com.seerbit.seerbit.assessment.controller;

import com.seerbit.seerbit.assessment.dto.ResponseDTO;
import com.seerbit.seerbit.assessment.dto.TransactionRequest;
import com.seerbit.seerbit.assessment.exception.CustomException;
import com.seerbit.seerbit.assessment.model.TransactionStatistics;
import com.seerbit.seerbit.assessment.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<?> addTransaction(@RequestBody TransactionRequest request) {
        try {
            transactionService.createTransaction(request);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request");
        }
    }

    @GetMapping("/statistics")
    public ResponseEntity<?> getTransactionStatistics() {
        TransactionStatistics stats = transactionService.getTransactionStatistics();
        return ResponseEntity.ok(stats);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.deleteTransactions();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
