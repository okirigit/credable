package com.interview.credable.lms.domain;



import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Loan {

    private Long id;
    private String customerNumber;
    private Double amount;
    private String status;
    private LocalDateTime applicationDate;
    private BigDecimal approvedLimit;
    private Integer score;

}
