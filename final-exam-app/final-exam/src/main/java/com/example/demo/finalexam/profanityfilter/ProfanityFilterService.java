package com.example.demo.finalexam.profanityfilter;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demo.finalexam.product.model.Product;

@Service
public class ProfanityFilterService  {

    private final RestTemplate restTemplate;

    // private final String ACCEPT_HEADER = HttpHeaders.ACCEPT;
    // private final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;
    @Value("${profanityfilter.url}")
    private String URL;
    @Value("${profanityfilter.text-param}")
    private String TEXT_PARAM;
    @Value("${profanityfilter.x-api-key-header}")
    private String X_API_KEY_HEADER;
    @Value("${profanityfilter.x-api-key}")
    private String X_API_KEY;

    public ProfanityFilterService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Product execute(Product product) {
        String name = product.getName();
        String description = product.getDescription();

        ProfanityFilterResponse nameResponse = this.filterProfanity(name);
        ProfanityFilterResponse descriptionResponse = this.filterProfanity(description);

        product.setName(nameResponse.getCensored());
        product.setDescription(descriptionResponse.getCensored());
        // if (nameResponse.isHasProfanity()) {
        //     product.setName(nameResponse.getCensored());
        // }
        // if (descriptionResponse.isHasProfanity()) {
        //     product.setDescription(descriptionResponse.getCensored());
        // }

        return product; 
    }

    public ProfanityFilterResponse filterProfanity(String text) {
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam(TEXT_PARAM, text)
                .build()
                .toUri();

        // headers
        HttpHeaders headers = new HttpHeaders();
        // headers.set(ACCEPT_HEADER, APPLICATION_JSON);
        headers.set(X_API_KEY_HEADER, X_API_KEY);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // handle error response
        try {
            ResponseEntity<ProfanityFilterResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
                    ProfanityFilterResponse.class);

            ProfanityFilterResponse profanityFilterResponse = response.getBody();
            if (profanityFilterResponse == null) {
                throw new RuntimeException("Received null response body from Profanity Filter API");
            }

            return profanityFilterResponse;

        } catch (Exception e) {
            throw new RuntimeException("Failed to get profanity filter api: ", e);
        }
    }
}