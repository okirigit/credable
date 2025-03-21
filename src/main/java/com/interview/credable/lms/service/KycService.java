package com.interview.credable.lms.service;


import io.credable.cbs.customer.Customer;

import java.util.List;

public interface KycService {

    /**
     * Initiates the scoring process for a given customer.
     *
     * @param customerNumber The customer's number.
     * @return A token representing the initiated scoring process.
     */
    Customer getCustomerDetails(String customerNumber);

    /**
     * Queries the scoring engine for the score using the provided token.
     *
     * @param token The token obtained from the initiateScoring step.
     * @return The scoring response, or null if the score is not available after retries.
     */

}