package com.interview.credable.lms.domain;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "users")
@Data
public class Customer {

    @Id
    private String id;
    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^(254|0)?(7(?:(?:[0-2]|[9][0-9]))[0-9]{7})$", message = "Invalid Kenyan phone number format")
    private String customerNumber;
    @NotBlank(message = "First Name is required")
    private String firstName;
    @NotBlank(message = "Last Name is required")
    private String lastName;
    @NotBlank(message = "Email Address is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")
    private String email;
    private String createdOn;

}
