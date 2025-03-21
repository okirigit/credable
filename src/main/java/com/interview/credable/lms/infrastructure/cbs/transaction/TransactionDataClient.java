package com.interview.credable.lms.infrastructure.cbs.transaction;


import io.credable.cbs.transaction.TransactionDataPort;
import io.credable.cbs.transaction.TransactionDataPortService;
import io.credable.cbs.transaction.TransactionsRequest;
import io.credable.cbs.transaction.TransactionsResponse;
import org.springframework.stereotype.Service;

@Service
public class TransactionDataClient {

    public TransactionsResponse getTransactionData(String customerNumber) {

        TransactionDataPortService service = new TransactionDataPortService();
        TransactionDataPort port = service.getTransactionDataPortSoap11();
        TransactionsRequest request = new TransactionsRequest();
        request.setCustomerNumber(customerNumber);
        TransactionsResponse response = port.transactions(request);

        return response;
    }


}