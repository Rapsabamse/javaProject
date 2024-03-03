package com.model;

import org.springframework.http.HttpStatusCode;

public record ServiceTest(String message, HttpStatusCode code) { }
