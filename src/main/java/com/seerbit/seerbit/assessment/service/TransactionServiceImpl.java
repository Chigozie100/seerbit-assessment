package com.seerbit.seerbit.assessment.service;

import com.seerbit.seerbit.assessment.dto.TransactionRequest;
import com.seerbit.seerbit.assessment.exception.CustomException;
import com.seerbit.seerbit.assessment.model.Transaction;
import com.seerbit.seerbit.assessment.model.TransactionStatistics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.LinkedList;
import java.util.concurrent.locks.ReentrantLock;

@Slf4j
@Service
public class TransactionServiceImpl implements TransactionService {

    private static final int INTERVAL = 30;
    private final LinkedList<Transaction> transactions = new LinkedList<>();
    private final ReentrantLock lock = new ReentrantLock();

    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal max = BigDecimal.ZERO;
    private BigDecimal min = new BigDecimal(Double.MAX_VALUE);
    private long count = 0;


    @Override
    public Transaction createTransaction(TransactionRequest transactionRequest) {
        log.info("Creating transaction {}", transactionRequest);
        lock.lock();
        try {
            BigDecimal amount = new BigDecimal(transactionRequest.amount());
            Instant timestamp = Instant.parse(transactionRequest.timestamp());

            Instant now = Instant.now();
            if (timestamp.isAfter(now)) {
                log.info("Transaction date is in the future");
                throw new CustomException(422, "Transaction date is in the future.");
            }

            if (now.minusSeconds(INTERVAL).isAfter(timestamp)) {
                log.info("Transaction is older than 30 seconds");
                throw new CustomException(204, "Transaction is older than 30 seconds.");
            }

            Transaction transaction = new Transaction();
            transaction.setAmount(amount);
            transaction.setTimestamp(timestamp);

            transactions.add(transaction);
            updateStatisticsOnAdd(amount);
            log.info("Transaction created");
            return transaction;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public TransactionStatistics getTransactionStatistics() {
        log.info("Getting transaction statistics");
        lock.lock();
        try {
            Instant now = Instant.now();

            // Remove old transactions
            while (!transactions.isEmpty() && now.minusSeconds(INTERVAL).isAfter(transactions.getFirst().getTimestamp())) {
                Transaction transaction = transactions.removeFirst();
                updateStatisticsOnRemove(transaction.getAmount());
            }

            // Calculate average
            BigDecimal avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP) : BigDecimal.ZERO;

            // Round sum, max, and min values to two decimal places
            BigDecimal roundedSum = sum.setScale(2, RoundingMode.HALF_UP);
            BigDecimal roundedMax = max.equals(BigDecimal.ZERO) ? BigDecimal.ZERO : max.setScale(2, RoundingMode.HALF_UP);
            BigDecimal roundedMin = min.equals(new BigDecimal(Double.MAX_VALUE)) ? BigDecimal.ZERO : min.setScale(2, RoundingMode.HALF_UP);

            log.info("Total transaction statistics: {}", roundedSum);
            return new TransactionStatistics(roundedSum, avg, roundedMax, roundedMin, count);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteTransactions() {
        log.info("Deleting transactions");
        lock.lock();
        try {
            transactions.clear();
            sum = BigDecimal.ZERO;
            max = BigDecimal.ZERO;
            min = new BigDecimal(Double.MAX_VALUE);
            count = 0;
            log.info("Transactions deleted");
        } finally {
            lock.unlock();
        }
    }

    private void updateStatisticsOnAdd(BigDecimal amount) {
        sum = sum.add(amount);
        max = max.max(amount);
        min = min.min(amount);
        count++;
    }

    private void updateStatisticsOnRemove(BigDecimal amount) {
        sum = sum.subtract(amount);

        if (count == 1) {
            // Reset statistics if the last transaction is removed
            max = BigDecimal.ZERO;
            min = new BigDecimal(Double.MAX_VALUE);
        }
        count--;
    }

}
