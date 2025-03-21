package com.interview.credable.lms.service;


import com.interview.credable.lms.domain.ScoreResponse;
import com.interview.credable.lms.utils.ResponseObject;

public interface ScoringService {

    /**
     * Initiates the scoring process for a given customer.
     *
     * @param customerNumber The customer's number.
     * @return A token representing the initiated scoring process.
     */
    String initiateScoring(String customerNumber);

    /**
     * Queries the scoring engine for the score using the provided token.
     *
     * @param token The token obtained from the initiateScoring step.
     * @return The scoring response, or null if the score is not available after retries.
     */
    ScoreResponse getScore(String token);

    ResponseObject getStatus(String token);
}