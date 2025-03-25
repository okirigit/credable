package com.interview.credable.lms.controller;


import com.interview.credable.lms.config.ScoringServiceClient;
import com.interview.credable.lms.domain.Customer;
import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.service.LoanService;
import com.interview.credable.lms.service.UserService;
import com.interview.credable.lms.utils.AmqMessageProducer;
import com.interview.credable.lms.utils.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("api/v1/loans")
public class LoansController {

    private final ScoringServiceClient scoringServiceClient;
    private final UserService userService;
    private final LoanService loanService;

    private final AmqMessageProducer amqMessageProducer;

    @Autowired
    public LoansController(ScoringServiceClient scoringServiceClient,
                           UserService userService, LoanService loanService,
                           AmqMessageProducer amqMessageProducer) {
        this.scoringServiceClient = scoringServiceClient;
        this.userService = userService;
        this.loanService = loanService;

        this.amqMessageProducer = amqMessageProducer;
    }

    @PostMapping("/subscribe")
    public ResponseObject createUser(@RequestBody Customer customer) {
        return userService.subscribeCustomer(customer);
    }

    @GetMapping ("/allUsers")
    public List<Customer> getUsers() {
        return userService.getAllCustomers();
    }

    @PostMapping("/request")
    public ResponseEntity<ResponseObject> requestLoan(@RequestBody Loan loanRequest) {
        return amqMessageProducer.sendLoanApplicationMessage(loanRequest);
    }

    @GetMapping("/status/{customerNumber}")
    public ResponseEntity<?> getLoanStatus(@PathVariable String customerNumber) {
        return loanService.getStatus(customerNumber);
    }
}