package com.interview.credable.lms.service;


import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.repositories.LoansRepository;
import com.interview.credable.lms.utils.AmqMessageProducer;
import com.interview.credable.lms.utils.ResponseObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
public class LoanService {
    private final LoansRepository loansRepository;
    private final AmqMessageProducer amqMessageProducer;
    public LoanService(LoansRepository loansRepository, AmqMessageProducer amqMessageProducer) {
        this.loansRepository = loansRepository;
        this.amqMessageProducer = amqMessageProducer;
    }
    public List<Loan> getAllLoans() {
        return loansRepository.findAll();
    }


    public ResponseObject processLoanRequest(Loan loan) {
        ResponseObject resp = new ResponseObject();
        long currentTimeMillis = System.currentTimeMillis();
        loan.setId(currentTimeMillis);
        loan.setReplays(0);
        loan.setStatus("PENDING");
        try {
            loansRepository.save(loan);
            amqMessageProducer.sendScoringRequestMessage(loan);
            log.info("Saved Loan Application: {}", loan);
            resp.setSuccess(true);
            resp.setMessage("Loan request  successfully Received. Check your app for status");
        }catch (Exception e) {
            log.info("Failed to Save Loan Application: {}", e.getMessage());
            resp.setSuccess(false);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    public ResponseEntity<?> getStatus(String customerNumber) {
        log.info(customerNumber);
        Optional<Loan> loanRequest = loansRepository.findLoansByCustomerNumberAndStatus(customerNumber,"PENDING");
        if(loanRequest.isPresent()) {
            return ResponseEntity.ok(loanRequest.get());
        } else {

            return ResponseEntity.notFound().build();
        }
    }
}
