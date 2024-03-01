package com.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.*;
import com.service.imageFiltersService;


@RestController
public class Controller {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	imageFiltersService imageFilterService = new imageFiltersService();

	/**
	 * @param name
	 * @return Hello! {name}
	 */
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	/**
	 * @param service
	 * @return {message: a message of status, code: http response code}
	 */
	@GetMapping("/testService")
	public ServiceTest serviceTest(@RequestParam(value = "service", defaultValue = "blur") String service) {
		if("blur".equals(service)){
			ServiceResponse test = imageFilterService.testBlur();
			return new ServiceTest(test.message(), test.Code());
		}

		return new ServiceTest("Error", 400);
	}
}