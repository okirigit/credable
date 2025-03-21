package com.interview.credable.lms;

import com.interview.credable.lms.config.ScoringServiceClient;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;

@Slf4j
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class})
public class CredableApplication {

	private final ScoringServiceClient scoringServiceClient;

    public CredableApplication(ScoringServiceClient scoringServiceClient) {
        this.scoringServiceClient = scoringServiceClient;
    }

    public static void main(String[] args) {
		SpringApplication.run(CredableApplication.class, args);
	}
	@PostConstruct
	public void registerClient() {
		try {
			log.info("Registering client:::::::::::::...");
			scoringServiceClient.createClient();
		} catch (Exception e) {

			System.err.println("Error registering client: " + e.getMessage());

		}
	}
}
