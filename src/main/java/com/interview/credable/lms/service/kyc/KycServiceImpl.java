package com.interview.credable.lms.service.kyc;


import com.fasterxml.jackson.databind.ObjectMapper;
import io.credable.cbs.customer.*;
import jakarta.xml.ws.BindingProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Base64;
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

            CustomerPortService service = new CustomerPortService();

            ObjectMapper mapper = new ObjectMapper();
            CustomerPort port = service.getCustomerPortSoap11();
            CustomerRequest customerRequest = new CustomerRequest();
            customerRequest.setCustomerNumber(customerNumber);
            log.info("Get customer details: {}",mapper.writeValueAsString(customerRequest) );
            customerRequest.setCustomerNumber(customerNumber);
            Map<String, Object> requestContext = ((BindingProvider) port).getRequestContext();
            String authString = username + ":" + password;
            String authStringEnc = Base64.getEncoder().encodeToString(authString.getBytes());
            requestContext.put("Authorization", "Basic " + authStringEnc);
            CustomerResponse resp = port.customer(customerRequest);
            log.info("getCustomerDetails customerNumber: {}",((BindingProvider) port).getEndpointReference() );
            return resp.getCustomer();
        } catch (Exception e) {
            log.info("Error getting customer details {}",e.getMessage());
           return null;
        }
    }
}