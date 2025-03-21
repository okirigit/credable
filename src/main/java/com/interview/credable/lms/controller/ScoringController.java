package com.interview.credable.lms.controller;




import com.interview.credable.lms.service.TransactionDataServiceImpl;
import com.interview.credable.lms.service.KycServiceImpl;
import io.credable.cbs.customer.Customer;
import io.credable.cbs.transaction.TransactionsResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scoring")
@Slf4j
public class ScoringController {

    private final KycServiceImpl kycService;
    private final TransactionDataServiceImpl transactionDataService;

    @Autowired
    public ScoringController(KycServiceImpl kycService, TransactionDataServiceImpl transactionDataService) {
        this.kycService = kycService;
        this.transactionDataService = transactionDataService;
    }

    /**
     * API to fetch customer KYC details.
     *
     * @param customerNumber The customer number to query for.
     * @return A ResponseEntity containing the CustomerDetails.
     */
    @GetMapping("/kyc/{customerNumber}")
    public ResponseEntity<Customer> getCustomerKyc(@PathVariable String customerNumber) {

        log.info("getCustomerKyc customerNumber: " + customerNumber);
        try {
            Customer customerDetails = kycService.getCustomerDetails(customerNumber);
            if (customerDetails != null) {
                return ResponseEntity.ok(customerDetails);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., logging, error response)
            System.err.println("Error fetching KYC data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * API to fetch transaction data for a customer.
     *
     * @param customerNumber The customer number to query for.
     * @return A ResponseEntity containing the TransactionsResponse.
     */


    @GetMapping("/transactions/{customerNumber}")
    public ResponseEntity<TransactionsResponse> getCustomerTransactions(@PathVariable String customerNumber) {
        try {
            TransactionsResponse transactionsResponse = (TransactionsResponse) transactionDataService.transactionData(customerNumber);
            if (transactionsResponse != null) {
                log.info("Found {} transactions for CustomerNumber {}: ",transactionsResponse.getTransactions().size(), customerNumber);
                return ResponseEntity.ok(transactionsResponse);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., logging, error response)
            log.info("Error fetching transaction data: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}