package com.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.ServiceResponse;


@RestController
public class Controller {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * @return {message: a message of status, code: http response code}
	 */
	@GetMapping("/test")
	public ServiceResponse test() {
        return new ServiceResponse("ImageProcessor-service works!!", 200);
    }
}
