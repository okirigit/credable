package com.interview.credable.lms.domain;



import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data

public class Loan implements Serializable {

    @Null
    private Long id;
    @NotNull
    private String customerNumber;
    @NotNull
    private Double amount;
    @Null
    private String status;
    @Null
    private String applicationDate;
    @Null
    private Double approvedLimit;
    @Null
    private Integer score;
    @Null
    private int replays;

}
