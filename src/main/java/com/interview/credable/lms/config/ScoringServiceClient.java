package com.interview.credable.lms.config;




import com.fasterxml.jackson.databind.ObjectMapper;
import com.interview.credable.lms.domain.client.ClientRegistrationRequest;
import com.interview.credable.lms.domain.client.ClientRegistrationResponse;
import com.interview.credable.lms.domain.ScoreResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@Component
@Slf4j
public class ScoringServiceClient {

    public HashMap<String, String> ckientToken;

    private final ObjectMapper objectMapper;

    private final RestTemplate restTemplate;

    @Value("${scoring.service.baseurl}")
    private String baseUrl;



    @Value("${scoring.service.create-client}")
    private String createClientUrl;

    @Value("${scoring.service.url}")
    private String serviceUrl;

    @Value("${scoring.service.name}")
    private String serviceName;

    @Value("${scoring.service.username}")
    private String serviceUsername;

    @Value("${scoring.service.password}")
    private String servicePassword;

    public ScoringServiceClient(ObjectMapper objectMapper, RestTemplate restTemplate) {
        this.objectMapper = objectMapper;
        this.restTemplate = restTemplate;
        this.ckientToken = new HashMap<>();
    }

    public HashMap<String, String> initiateQueryScore(String customerNumber) {
        HashMap<String, String> params = new HashMap<>();
        String url = baseUrl + "/initiateQueryScore/" + customerNumber;
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", ckientToken.get("client-token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.GET, entity, String.class);
        if (response.getStatusCode() == HttpStatus.OK) {
            params.put("token", response.getBody());
            params.put("status", "success");
        }else {
            params.put("status", "failed");
            params.put("token",null);
        }
        return params;
    }

    public ScoreResponse queryScore(String token) {
        String url = baseUrl + "/queryScore/" + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("client-token", ckientToken.get("client-token"));
        HttpEntity<String> entity = new HttpEntity<>(headers);
        ResponseEntity<ScoreResponse> response = restTemplate.exchange(url, HttpMethod.GET, entity, ScoreResponse.class);
        return response.getBody();
    }

    public void createClient() {

        String url = baseUrl + createClientUrl; // Assuming createClientUrl contains the relative path


        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON); // Set content type to JSO

        ClientRegistrationRequest request = new ClientRegistrationRequest();
        request.setUrl(serviceUrl);
        request.setName(serviceName);
        request.setUsername(serviceUsername);
        request.setPassword(servicePassword);
        HttpEntity<ClientRegistrationRequest> entity = new HttpEntity<>(request, headers);
         try {
            ResponseEntity<String> response = restTemplate.exchange(
                    url,
                    HttpMethod.POST,
                    entity,
                    String.class // Assuming you have a ClientRegistrationResponse DTO
            );
            if (response.getStatusCode().is2xxSuccessful()) {
                log.info("Client registration response: {}", response);
                ClientRegistrationResponse registrationResponse = objectMapper.convertValue(response, ClientRegistrationResponse.class);
                ckientToken.put("token",registrationResponse.getToken());
                ckientToken.put("status","success");
                ckientToken.put("username",registrationResponse.getUsername());
                ckientToken.put("password",registrationResponse.getPassword());
                log.info("Client registered successfully. Token: {}",registrationResponse.getToken() );
            } else {
               log.info("Error registering client: {}" , response.getStatusCode());
            }
        } catch (Exception e) {
            log.info("Exception during client registration: {}" , e.getMessage());

              }
    }


}