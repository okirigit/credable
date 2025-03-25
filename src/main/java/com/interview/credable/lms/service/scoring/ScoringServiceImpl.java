package com.interview.credable.lms.service.scoring;


import com.interview.credable.lms.config.ScoringServiceClient;
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
    public ResponseObject getStatus(String token) {
        ResponseObject resp = new ResponseObject();
        resp.setStatus("Pending");
        resp.setSuccess(true);
        resp.setMessage("Loan request submitted successfully.");
        return resp;
    }

}