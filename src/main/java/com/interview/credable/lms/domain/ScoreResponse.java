package com.interview.credable.lms.domain;


import lombok.Data;

@Data
public class ScoreResponse {
        private int id;
        private String customerNumber;
        private int score;
        private double limitAmount;
        private String exclusion;
        private String exclusionReason;
}
