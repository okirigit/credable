package com.interview.credable.lms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.credable.lms.config.ScoringServiceClient;
import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.domain.ScoreResponse;
import com.interview.credable.lms.repositories.LoansRepository;
import com.interview.credable.lms.service.LoanService;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.ErrorHandler;

import java.util.HashMap;

@Component
@Slf4j
public class AmqMessageConsumer {

    private final LoansRepository loansRepository;
    private final ObjectMapper objectMapper;
    private final LoanService loanService;
    private final AmqMessageProducer amqMessageProducer;
    private final ScoringServiceClient scoringServiceClient;
    @Value("${spring.activemq.maxRetries}")
    private int maxRetries;


    public AmqMessageConsumer(LoansRepository loansRepository, ObjectMapper objectMapper, LoanService loanService, AmqMessageProducer amqMessageProducer, ScoringServiceClient scoringServiceClient) {
        this.loansRepository = loansRepository;
        this.objectMapper = objectMapper;
        this.loanService = loanService;
        this.amqMessageProducer = amqMessageProducer;
        this.scoringServiceClient = scoringServiceClient;
    }

    @JmsListener(destination = "${spring.activemq.loansRequestQueue}")
    public void handleLoanApplication(Message message) throws JMSException, JsonProcessingException {
        try {
            Loan loan = objectMapper.readValue(message.getBody(Object.class).toString(), Loan.class);
            loanService.processLoanRequest(loan);
            log.info("Received Loan Application: {}", loan.getApplicationDate());

        } catch (Exception e) {
            log.info("Error processing Loan Application {} ", e.getMessage());
        }
    }

    @JmsListener(destination = "${spring.activemq.scoreRequestQueue}")
    public void handleScoreApplication(Message message) throws JMSException, JsonProcessingException {
        Loan loan = objectMapper.readValue(message.getBody(Object.class).toString(), Loan.class);
        HashMap<String, String> initiateQueryScoreResult = scoringServiceClient.initiateQueryScore(loan.getCustomerNumber());
        if (initiateQueryScoreResult.get("token") != null) {
            try {
                ScoreResponse scoreResponse = scoringServiceClient.queryScore(initiateQueryScoreResult.get("token"));
                loan.setScore(scoreResponse.getScore());
                loan.setApprovedLimit(scoreResponse.getLimitAmount());

            } catch (Exception e) {
                //Retry mechanisim. Keep the item in queue untill retries have capped the limit
                if (loan.getReplays() < maxRetries) {
                    loan.setReplays(loan.getReplays() + 1);
                    amqMessageProducer.sendScoringRequestMessage(loan);

                } else {
                    loan.setStatus("FAILED");
                    log.info("Application has reached maximum retries. Failing application");
                }
                log.info("Error processing Score Application {} ", e.getMessage());
            }


        } else {

            if (loan.getReplays() < maxRetries) {
                loan.setReplays(loan.getReplays() + 1);
                amqMessageProducer.sendScoringRequestMessage(loan);

            } else {
                loan.setStatus("FAILED");
                log.info("Application has reached maximum retries. Failing application");


            }

        }
        loansRepository.save(loan);


        log.info("Preparing to query credit Engine Score and Limit for ::::::: {}", loan.getCustomerNumber());
    }

    @Bean
    public ErrorHandler myJmsErrorHandler() {
        return new MyJmsErrorHandler();
    }
}
