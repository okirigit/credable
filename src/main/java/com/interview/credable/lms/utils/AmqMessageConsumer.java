package com.interview.credable.lms.utils;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AmqMessageConsumer {

    @JmsListener(destination = "loan.application.queue", containerFactory = "jmsListenerContainerFactory")
    public void handleLoanApplication(String message) {
        System.out.println("Received Loan Application: " + message);
    }

    @JmsListener(destination = "scoring.response.queue", containerFactory = "jmsListenerContainerFactory")
    public void handleScoringResponse(String message) {
        System.out.println("Received Scoring Response: " + message);
    }
}
