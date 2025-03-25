package com.interview.credable.lms.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity

public class SecurityConfig {

@Value("${scoring.service.username}")
private String username;

@Value("${scoring.service.password}")
private String password;


@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http
            .authorizeHttpRequests((authz) -> authz
                    .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
    return http.build();
}

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {

    log.info(username + ":" + password);
        UserDetails user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))

                .build();
        return new InMemoryUserDetailsManager(user);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }




}

