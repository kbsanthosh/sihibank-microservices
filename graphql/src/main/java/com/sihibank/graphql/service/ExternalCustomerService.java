package com.sihibank.graphql.service;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import java.util.Map;

@Service
public class ExternalCustomerService {

    private final WebClient webClient;

    public ExternalCustomerService() {
        // 1. Clears AbstractJackson2Decoder deprecation: Use consumer configuration streams
        // to cleanly extend raw text support directly via Spring 7 WebClient protocols
        this.webClient = WebClient.builder()
                .baseUrl("http://localhost:8072")
                .codecs(configurer -> configurer.defaultCodecs())
                .build();
    }

    // 2. Clears Parameterised type argument suggestion: Replace explicit types with empty diamond tokens <>
    public Mono<Map<String, Object>> getRawCustomerDetails(String mobileNumber) {
        return this.webClient.get()
                .uri("/sihibank/accounts/api/fetchCustomerDetails?mobileNumber={mobileNumber}", mobileNumber)
                .accept(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML, MediaType.TEXT_PLAIN)
                .retrieve()
                // Use a modern, empty diamond parameter reference block to satisfy type inference
                .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
