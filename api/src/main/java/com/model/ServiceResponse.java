package com.model;

import org.springframework.http.HttpStatusCode;

public record ServiceResponse(String response, HttpStatusCode code) { }
