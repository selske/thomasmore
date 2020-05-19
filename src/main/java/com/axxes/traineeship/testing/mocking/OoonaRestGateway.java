package com.axxes.traineeship.testing.mocking;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.Optional;
import java.util.function.Function;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;
import static com.fasterxml.jackson.core.JsonGenerator.Feature.IGNORE_UNKNOWN;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;
import static com.fasterxml.jackson.databind.SerializationFeature.WRITE_DATES_AS_TIMESTAMPS;

public class OoonaRestGateway {

    private final ObjectMapper objectMapper;

    private final HttpClient httpClient;
    private final String oonaEndpoint;

    OoonaRestGateway(HttpClient httpClient, String ooonaEndpoint) {
        this.httpClient = httpClient;

        this.objectMapper = new ObjectMapper()
                .enable(IGNORE_UNKNOWN)
                .disable(FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(WRITE_DATES_AS_TIMESTAMPS)
                .setSerializationInclusion(NON_EMPTY)
                .registerModule(new JavaTimeModule());
        this.oonaEndpoint = ooonaEndpoint;
    }

    public int createOrder(CreateOrderRequest createOrderRequest) {
        return post("/CreateOrder", createOrderRequest, new TypeReference<OrderResponse>() {})
                .map(OrderResponse::getOrderNumber)
                .orElseThrow();
    }

    private <T> Function<HttpResponse<String>, Optional<T>> parseResponse(final TypeReference<T> typeReference) {
        return response -> {
            String responseBody = response.body();
            try {
                return Optional.of(objectMapper.readValue(responseBody, typeReference));
            } catch (JsonProcessingException e) {
                throw new IllegalArgumentException("Unable to parse response", e);
            }
        };
    }

    private <T> Function<HttpResponse<String>, Optional<T>> handleNoSuccess() {
        return response -> {
            if (response.statusCode() == 404) {
                return Optional.empty();
            }
            throw new RuntimeException("Got status code " + response.statusCode());
        };
    }

    private <T> Optional<T> post(String uri, Object body, TypeReference<T> typeReference) {
        final String requestBody;
        try {
            requestBody = objectMapper.writeValueAsString(body);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException("Unable to marshall request body", e);
        }
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(oonaEndpoint + uri))
                .header("Content-Type", "application/json")
                .header("Accept", "application/json")
                .POST(BodyPublishers.ofString(requestBody))
                .build();

        return send(request, parseResponse(typeReference), handleNoSuccess());
    }

    private <T> Optional<T> send(final HttpRequest request,
                                 final Function<HttpResponse<String>, Optional<T>> on200Success,
                                 final Function<HttpResponse<String>, Optional<T>> onEverythingElse) {
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return on200Success.apply(response);
            }
            return onEverythingElse.apply(response);
        } catch (Exception e) {
            throw new RuntimeException("Error sending request", e);
        }
    }

}
