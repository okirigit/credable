package com.interview.credable.lms.utils;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class AmqMessageProducer {

    private final JmsTemplate jmsTemplate;

    public AmqMessageProducer(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void sendLoanApplicationMessage(String customerNumber, double amount) {
        jmsTemplate.convertAndSend("loan.application.queue",
                "Customer: " + customerNumber + ", Amount: " + amount);
    }

    public void sendScoringRequestMessage(String customerNumber) {
        jmsTemplate.convertAndSend("scoring.request.queue", customerNumber);
    }

    // ... other methods for sending messages
}