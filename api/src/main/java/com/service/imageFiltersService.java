package com.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.model.*;

@Service
public class imageFiltersService {
    //create a restTemplate for creating a http request
    RestTemplate restTemplate = new RestTemplate();
    String URL = "http://localhost:8081/";

    //Create mapper to get specific data from responses
    ObjectMapper mapper = new ObjectMapper();

    /**
     * Function used send an image for imageprocessing to blur
     * Then recieves the blurred image from the imageprocessing service
     * 
     * @type POST
     * 
     * @return Blurred image (in base64 format)
     */
    public ServiceResponse blurImage(){
        String blurUrl = URL + "/test";

        ResponseEntity<String> response = restTemplate.getForEntity(blurUrl, String.class);
        String image = getMessage(response, "image");

        return new ServiceResponse(image, response.getStatusCode());
    }


    /**
     * Function used to test if connection with image processer is functioning
     * 
     * @type GET
     * 
     * @return ServiceTest - message: a message of status, code: http status code}
     */
    public ServiceTest testImageProcessor(){
        String serviceUrl = URL + "/test";

        ResponseEntity<String> response = restTemplate.getForEntity(serviceUrl, String.class);
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

            return message.asText();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "Error";
    }
}