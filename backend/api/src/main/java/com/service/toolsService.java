package com.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.*;

/**
 * Class responsible for service layer of tools in API
 */
@Service
public class toolsService {
    //create a restTemplate for creating a http request
    RestTemplate restTemplate = new RestTemplate();
    String URL = "http://localhost:8082";

    //Create mapper to get specific data from responses
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Function used to test if connection with image processer is functioning
     * 
     * @return {message: a message of status, code: http response code}
     */
    public ServiceTest testTools(){
        String toolUrl = URL + "/test";
        ResponseEntity<String> response = restTemplate.getForEntity(toolUrl, String.class);

        String message = getMessage(response, "message");

        return new ServiceTest(message, response.getStatusCode());
    }

    /**
     * Helper function used to extract data from a field in a ResponseEntity object
     * 
     * @param response Response object from request
     * @param filter   Name/field to be extracted
     * @return         Data contained in extracted name/field
     */
    private String getMessage(ResponseEntity<String> response, String filter){
        String content = response.getBody();

        try {
            JsonNode temp = mapper.readTree(content);
            
            JsonNode message = temp.path(filter);

            System.err.println(message);

            return message.asText();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return "Error";
    }
}