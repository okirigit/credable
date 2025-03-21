package com.interview.credable.lms.config;

import jakarta.jms.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.boot.autoconfigure.jms.JmsProperties;

@Slf4j
@Configuration
public class JmsConfig {

    @Bean
    public JmsListenerContainerFactory<?> jmsListenerContainerFactory(
            @Qualifier("jmsConnectionFactory") ConnectionFactory connectionFactory,
            DefaultJmsListenerContainerFactoryConfigurer configurer,
            JmsProperties jmsProperties) {
        try{
            DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
            configurer.configure(factory, connectionFactory);
            factory.setPubSubDomain(false);  // Set to true for topics

            return factory;
        }catch (Exception e) {

            log.info("Error creating JmsListenerContainerFactory");
       return null;
        }

    }

    @Bean // Serialize message content to json using TextMessage
    public MessageConverter jacksonJmsMessageConverter() {
        try{
            MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
            converter.setTargetType(MessageType.TEXT);
            converter.setTypeIdPropertyName("_type");
            return converter;
        }catch (Exception e) {
            log.info("Error creating Jackson2MessageConverter");
            return null;
        }

    }
}