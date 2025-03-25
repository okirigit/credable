package com.interview.credable.lms.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.repositories.LoansRepository;
import jakarta.jms.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Optional;


@Slf4j
@Component
public class AmqMessageProducer {

    private final ObjectMapper mapper = new ObjectMapper();

    private final JmsTemplate jmsTemplate;

    @Value("${spring.activemq.loansRequestQueue}")
    private String QUEUE_LOANS_REQUEST ;

    @Value("${spring.activemq.scoreRequestQueue}")
    private String SCORE_REQUEST_QUEUE ;
    private final LoansRepository loansRepository;

    public AmqMessageProducer(JmsTemplate jmsTemplate, LoansRepository loansRepository) {
        this.jmsTemplate = jmsTemplate;

        this.loansRepository = loansRepository;
    }

    public ResponseEntity<ResponseObject> sendLoanApplicationMessage(Loan loan) {
        ResponseObject responseObject = new ResponseObject();
        ResponseEntity<ResponseObject> responseEntity ;
        Optional<Loan> optionalLoan = loansRepository.findLoansByCustomerNumberAndStatus(loan.getCustomerNumber(),"PENDING");
        if(optionalLoan.isPresent()) {
            ResponseObject resp = new ResponseObject();
            //this means there is an existing request, cancel the current on and end the request
            resp.setMessage("Loan request  Declined due to an active request");
            log.info("Loan request  Declined due to an active request");
            resp.setSuccess(true);
            return new ResponseEntity<>(resp, HttpStatus.OK);
        }
        try {
            //check if user has loan request that is pending from mongodb

            String jsonString = mapper.writeValueAsString(loan);
            jmsTemplate.convertAndSend(QUEUE_LOANS_REQUEST, jsonString);
            log.info("Loan application for  Received from :::::::::::::::: {}", loan);
            responseObject.setMessage("Loan application   received Successfully.");
            responseObject.setStatus("SUCCESS");
            responseObject.setSuccess(Boolean.TRUE);
            responseEntity = new ResponseEntity<>(responseObject, HttpStatus.OK);
        }  catch (Exception e) {
            responseObject.setStatus("ERROR");
            responseObject.setMessage(e.getMessage());
            responseObject.setSuccess(Boolean.FALSE);
            responseEntity = new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
            log.error("Error while sending loan application message {}", e.getMessage());
        }
        return responseEntity;
    }

    public void sendScoringRequestMessage(Loan loan) {
        try{
            String message = mapper.writeValueAsString(loan);
            jmsTemplate.convertAndSend(SCORE_REQUEST_QUEUE, message);
        } catch (JsonProcessingException e) {
            log.error("Error while sending scoring request message {}", e.getMessage());
        }

    }



    // ... other methods for sending messages
}