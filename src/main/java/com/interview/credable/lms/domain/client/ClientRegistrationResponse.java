package com.interview.credable.lms.domain.client;


import lombok.Data;

@Data
public class ClientRegistrationResponse {
    private int id;
    private String url;
    private String name;
    private String username;
    private String password;
    private String token;

    @Override
    public String toString() {
        return "ClientRegistrationResponse{" +
                "id=" + id +
                ", url='" + url + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
