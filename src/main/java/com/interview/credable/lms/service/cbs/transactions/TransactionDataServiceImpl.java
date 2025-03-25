package com.interview.credable.lms.service.cbs.transactions;


import com.interview.credable.lms.config.ScoringServiceClient;

import io.credable.cbs.transaction.TransactionData;
import io.credable.cbs.transaction.TransactionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
                log.info("Found transaction {}", transaction);
            });
        } else {
            log.info("No transactions found or error occurred.:::::::::::::::");
        }
        return response.getTransactions();
    }
}