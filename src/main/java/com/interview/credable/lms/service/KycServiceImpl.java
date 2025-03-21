package com.interview.credable.lms.service;


import com.interview.credable.lms.config.ScoringServiceClient;
import com.interview.credable.lms.infrastructure.cbs.transaction.TransactionDataClient;
import io.credable.cbs.customer.*;
import io.credable.cbs.transaction.TransactionData;
import io.credable.cbs.transaction.TransactionsResponse;
import jakarta.xml.ws.BindingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class KycServiceImpl implements KycService {

    @Value("${cbs.auth.username}")
    private String username;

    @Value("${cbs.auth.password}")
    private String password;

    @Override
    public Customer getCustomerDetails(String customerNumber) {

        try {
            // 1. Create a service instance
            CustomerPortService service = new CustomerPortService();

            // 2. Get the port
            CustomerPort port = service.getCustomerPortSoap11();
           CustomerRequest customerRequest = new CustomerRequest();

            customerRequest.setCustomerNumber(customerNumber);
            Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
            String authString = username + ":" + password;
            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
            requestContext.put(BindingProvider.USERNAME_PROPERTY, username);
            requestContext.put(BindingProvider.PASSWORD_PROPERTY, password);
            requestContext.put("Authorization", "Basic " + authStringEnc);

            CustomerResponse resp = port.customer(customerRequest);
            // 3. Invoke the operation

            log.info("getCustomerDetails customerNumber: " );
            return resp.getCustomer();

        } catch (Exception e) {
            log.info("Error getting customer details");
            // Handle exceptions appropriately (log, throw, etc.)
           return null; // Or throw an exception if that's more suitable
        }

    }
}