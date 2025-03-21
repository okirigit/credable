package com.interview.credable.lms.controller;


import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.service.ScoringService;
import com.interview.credable.lms.utils.AmqMessageProducer;
import com.interview.credable.lms.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/loans")
public class LoansController {

    private final ScoringService loanApplicationService;

    private final AmqMessageProducer amqMessageProducer;

    @Autowired
    public LoansController(ScoringService loanApplicationService,
                           ScoringService scoringService,
                           AmqMessageProducer amqMessageProducer) {
        this.loanApplicationService = loanApplicationService;

        this.amqMessageProducer = amqMessageProducer;
    }


    @PostMapping("/request")
    public ResponseEntity<String> requestLoan(@RequestBody Loan loanRequest) {
        try {

            amqMessageProducer.sendLoanApplicationMessage(loanRequest.getCustomerNumber(), loanRequest.getAmount());
            return ResponseEntity.ok("Loan request submitted successfully.");
        } catch (Exception e) {
            // Handle exceptions (e.g., logging, error response)
            System.err.println("Error submitting loan request: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error submitting loan request.");
        }
    }


    @GetMapping("/status/{customerNumber}")
    public ResponseObject getLoanStatus(@PathVariable String customerNumber) {
        ResponseObject loanStatus = loanApplicationService.getStatus(customerNumber);
        return ResponseEntity.ok().body(loanStatus).getBody();
    }
}