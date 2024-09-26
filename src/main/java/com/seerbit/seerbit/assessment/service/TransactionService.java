package com.seerbit.seerbit.assessment.service;

import com.seerbit.seerbit.assessment.dto.TransactionRequest;
import com.seerbit.seerbit.assessment.model.Transaction;
import com.seerbit.seerbit.assessment.model.TransactionStatistics;

public interface TransactionService {
    Transaction createTransaction(TransactionRequest transactionRequest);

    TransactionStatistics getTransactionStatistics();

    void deleteTransactions();
}
