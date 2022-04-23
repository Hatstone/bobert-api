package com.hatstone.bobertapi.services;

import com.hatstone.bobertapi.dto.RunObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Collections;

@Service
public class RestService {
    private final RestTemplate restTemplate;

    public RestService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .setConnectTimeout(Duration.ofSeconds(500)) //Update me as needed
                .setReadTimeout(Duration.ofSeconds(500))    //Update me as needed
                .build();
    }

    public RunObject[] getRunObjects() {
        String url = "localhost:5000/run";
        return this.restTemplate.getForObject(url, RunObject[].class);
    }

    public String createRunObject(String language, String code, String args) {
        System.out.println("Inside RestService.createRunObject()");   //TODO: Remove me
        String url = "http://runner:5000/run";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RunObject runObj = new RunObject(language, code, args);
        HttpEntity<RunObject> entity = new HttpEntity<RunObject>(runObj, headers);
        return restTemplate.postForObject(url, entity, String.class);
    }
}
