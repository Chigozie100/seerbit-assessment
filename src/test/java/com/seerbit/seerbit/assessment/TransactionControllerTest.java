package com.seerbit.seerbit.assessment;

import com.seerbit.seerbit.assessment.controller.TransactionController;
import com.seerbit.seerbit.assessment.dto.TransactionRequest;
import com.seerbit.seerbit.assessment.model.TransactionStatistics;
import com.seerbit.seerbit.assessment.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class TransactionControllerTest {
    private TransactionService transactionService;
    private TransactionController transactionController;

    @BeforeEach
    void setUp() {
        transactionService = mock(TransactionService.class);
        transactionController = new TransactionController(transactionService);
    }

    @Test
    void testAddTransaction_Success() {
        // Arrange
        TransactionRequest request = new TransactionRequest("12.34", "2024-09-25T10:00:51.312Z");

        // Act
        ResponseEntity<?> response = transactionController.addTransaction(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(transactionService, times(1)).createTransaction(request);
    }

    @Test
    void testAddTransaction_InvalidRequest() {
        // Arrange
        TransactionRequest request = new TransactionRequest("invalid_amount", "invalid_timestamp");
        doThrow(new IllegalArgumentException("Invalid request")).when(transactionService).createTransaction(any());

        // Act
        ResponseEntity<?> response = transactionController.addTransaction(request);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid request", response.getBody());
    }

    @Test
    void testGetTransactionStatistics() {
        BigDecimal sum = new BigDecimal("277.36");
        BigDecimal avg = new BigDecimal("69.34");
        BigDecimal max = new BigDecimal("92.34");
        BigDecimal min = new BigDecimal("12.33");
        long count = 4;
        TransactionStatistics mockStats = new TransactionStatistics(sum, avg, max, min, count);

        when(transactionService.getTransactionStatistics()).thenReturn(mockStats);

        // Act
        ResponseEntity<?> response = transactionController.getTransactionStatistics();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStats, response.getBody());
        verify(transactionService, times(1)).getTransactionStatistics();
    }

    @Test
    void testDeleteTransactions() {
        // Act
        ResponseEntity<Void> response = transactionController.deleteTransactions();

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(transactionService, times(1)).deleteTransactions();
    }

}
