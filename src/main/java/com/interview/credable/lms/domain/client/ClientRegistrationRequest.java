package com.interview.credable.lms.domain.client;


import lombok.Data;

@Data
public class ClientRegistrationRequest {
    private String url;
    private String name;
    private String username;
    private String password;

    // No-args constructor
}