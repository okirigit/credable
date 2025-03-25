package com.interview.credable.lms.repositories;

import com.interview.credable.lms.domain.Customer;
import com.interview.credable.lms.domain.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoansRepository extends MongoRepository<Loan, String> {
     Optional<Loan> findByCustomerNumber(String customerNumber);
    @org.springframework.data.mongodb.repository.Query("{ 'customerNumber' : ?0, 'status' : ?1 }")
    Optional<Loan> findLoansByCustomerNumberAndStatus(String customerNumber, String status);


}