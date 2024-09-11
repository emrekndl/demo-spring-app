package com.example.demospringapp.catfact;

import java.net.URI;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.demospringapp.IQuery;

@Service
public class CatFactService implements IQuery<Integer, CatFactDTO> {

    private final RestTemplate restTemplate;

    private final String URL = "https://catfact.ninja/fact";
    private final String MAX_LENGTH = "max_length";
    private final String ACCEPT = HttpHeaders.ACCEPT;
    private final String APPLICATION_JSON = MediaType.APPLICATION_JSON_VALUE;

    public CatFactService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public ResponseEntity<CatFactDTO> execute(Integer input) {
        // getForObject is a GET request that returns the response body directly as the
        // specified type (in this case, CatFactResponse)
        // CatFactResponse response =
        // restTemplate.getForObject("https://catfact.ninja/fact?max_length="+ input,
        // CatFactResponse.class);
        // CatFactDTO catFactDTO = new CatFactDTO(response.getFact());

        // sets up url with query string params
        URI uri = UriComponentsBuilder
                .fromHttpUrl(URL)
                .queryParam(MAX_LENGTH, input)
                .build()
                .toUri();

        // headers
        HttpHeaders headers = new HttpHeaders();
        headers.set(ACCEPT, APPLICATION_JSON);

        HttpEntity<String> httpEntity = new HttpEntity<>(headers);

        // handle error response
        try {

            ResponseEntity<CatFactResponse> response = restTemplate.exchange(uri, HttpMethod.GET, httpEntity,
                    CatFactResponse.class);

            CatFactResponse catFactResponse = response.getBody();
            if (catFactResponse == null) {
                throw new RuntimeException("Received null response body from cat fact API");
            }

            CatFactDTO catFactDTO = new CatFactDTO(catFactResponse.getFact());

            return ResponseEntity.ok(catFactDTO);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get cat fact api: ", e);
        }
    }}
