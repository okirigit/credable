package com.interview.credable.lms.service;


import com.interview.credable.lms.config.ScoringServiceClient;
import com.interview.credable.lms.domain.ScoreResponse;
import com.interview.credable.lms.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScoringServiceImpl implements ScoringService {

    private final ScoringServiceClient scoringServiceClient;

    // Retry configuration (you can externalize these)
    private static final int MAX_RETRIES = 3;
    private static final long RETRY_DELAY_MS = 2000; // 2 seconds

    @Autowired
    public ScoringServiceImpl(ScoringServiceClient scoringServiceClient) {
        this.scoringServiceClient = scoringServiceClient;
    }

    @Override
    public String initiateScoring(String customerNumber) {
        return scoringServiceClient.initiateQueryScore(customerNumber);
    }

    @Override
    public ScoreResponse getScore(String token) {

        ScoreResponse scoringResponse = null;
        int retryCount = 0;

        while (retryCount < MAX_RETRIES) {
            scoringResponse = scoringServiceClient.queryScore(token);
            if (scoringResponse != null) {
                // Assuming ScoringResponse is not null if the score is available
                return scoringResponse;
            }

            retryCount++;
            try {
                Thread.sleep(RETRY_DELAY_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt(); // Restore interrupted status
                break;
            }
        }

        // If retries are exhausted, log and return null (or handle as needed)
        System.err.println("Scoring Engine did not return a score after " + MAX_RETRIES + " retries.");
        return null; // Or you might throw an exception here
    }

    @Override
    public ResponseObject getStatus(String token) {
        ResponseObject resp = new ResponseObject();
        resp.setStatus("Pending");
        resp.setSuccess(true);
        resp.setMessage("Loan request submitted successfully.");
        return resp;
    }

}