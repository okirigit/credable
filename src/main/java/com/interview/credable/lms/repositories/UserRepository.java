package com.interview.credable.lms.repositories;

import com.interview.credable.lms.domain.Customer;
import com.interview.credable.lms.domain.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<Customer, String> {
    Customer findByCustomerNumber(String customerNumber);
}