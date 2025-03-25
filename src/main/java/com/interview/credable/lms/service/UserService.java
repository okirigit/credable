package com.interview.credable.lms.service;

import com.interview.credable.lms.domain.Customer;
import com.interview.credable.lms.domain.Loan;
import com.interview.credable.lms.repositories.UserRepository;
import com.interview.credable.lms.utils.ResponseObject;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<Customer> getAllCustomers() {
        return userRepository.findAll();
    }

    public Customer getCustomerById(String id) {
        return userRepository.findById(id).orElse(null);
    }

    public ResponseObject subscribeCustomer(Customer customer) {
        ResponseObject resp = new ResponseObject();

        try {
            userRepository.save(customer);
            resp.setSuccess(true);
            resp.setMessage("Customer successfully subscribed");

        }catch (Exception e) {
            resp.setSuccess(false);
            resp.setMessage(e.getMessage());
        }
        return resp;
    }

    public void deleteLoan(String id) {
        userRepository.deleteById(id);
    }
}
