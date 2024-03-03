package com.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.model.*;
import com.service.*;


@RestController
public class Controller {
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	imageFiltersService imageFilterService = new imageFiltersService();
	toolsService toolsService = new toolsService();

	/**
	 * Used to test if connection to the API service is funcitoning
	 * 
	 * @param name
	 * @return Hello! {name}
	 */
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	/**
	 * Used to test if connection with the imageProccesor microservice is functioning
	 * 
	 * @return {message: a message of status, code: http response code}
	 */
	@GetMapping("/testImageProcessor")
	public ServiceTest processorServiceTest() {
		ServiceTest test = imageFilterService.testImageProcessor();
		return new ServiceTest(test.message(), test.code());
	}

	/**
	 * Used to test if connection with the tools microservice is functioning
	 * 
	 * @return {message: a message of status, code: http response code}
	 */
	@GetMapping("/testImageProcessor")
	public ServiceTest toolsServiceTest() {
		ServiceTest test = toolsService.testTools();
		return new ServiceTest(test.message(), test.code());
	}
}