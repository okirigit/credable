package com.interview.credable.lms.service.scoring;


import com.interview.credable.lms.utils.ResponseObject;

public interface ScoringService {

    ResponseObject getStatus(String token);
}