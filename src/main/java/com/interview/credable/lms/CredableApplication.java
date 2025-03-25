package com.interview.credable.lms;

import com.interview.credable.lms.config.ScoringServiceClient;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.JdbcTemplateAutoConfiguration;
import org.springframework.context.annotation.Import;

@Slf4j
@OpenAPIDefinition(
		info = @Info(title = "Credable LMS microservice interview", version = "v1.0", description = "Loan management system")
)
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class,
		DataSourceTransactionManagerAutoConfiguration.class,
		JdbcTemplateAutoConfiguration.class})

public class CredableApplication {

	private final ScoringServiceClient scoringServiceClient;

    public CredableApplication(ScoringServiceClient scoringServiceClient) {
        this.scoringServiceClient = scoringServiceClient;
    }

    public static void main(String[] args) {
		System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
		System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
		System.setProperty("com.sun.xml.ws.client.ContentNegotiation", "None");
		System.setProperty("com.sun.xml.ws.api.message.Packet.dump", "true");
		SpringApplication.run(CredableApplication.class, args);
	}
	@PostConstruct
	public void registerClient() {
		try {
			log.info("Registering client::::::::::::: ");
			scoringServiceClient.createClient();
		} catch (Exception e) {
			System.err.println("Error registering client: " + e.getMessage());

		}
	}
}
