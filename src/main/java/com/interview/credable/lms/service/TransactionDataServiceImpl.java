package com.interview.credable.lms.service;


import com.interview.credable.lms.config.ScoringServiceClient;

import com.interview.credable.lms.infrastructure.cbs.transaction.TransactionDataClient;
import io.credable.cbs.transaction.TransactionData;
import io.credable.cbs.transaction.TransactionsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionDataServiceImpl implements TransactionDataService {

    private final ScoringServiceClient scoringServiceClient;

    // Retry configuration (you can externalize these)
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000; // 2 seconds

    public TransactionDataServiceImpl(ScoringServiceClient scoringServiceClient) {
        this.scoringServiceClient = scoringServiceClient;
    }


    @Override
    public List<TransactionData> transactionData(String customerNumber) {
        TransactionDataClient client = new TransactionDataClient();
        TransactionsResponse response = client.getTransactionData(customerNumber);

        if (response != null && response.getTransactions() != null) {
            response.getTransactions().forEach(transaction -> {
                System.out.println("Account Number: " + transaction.getAccountNumber());
                System.out.println("Amount: " + transaction.getAlternativechanneltrnscrAmount());

            });
        } else {
            System.out.println("No transactions found or error occurred.");
        }
        return response.getTransactions();
    }
}