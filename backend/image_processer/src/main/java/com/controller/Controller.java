package com.controller;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;

import com.model.ServiceResponse;

import com.service.imageFiltersService;


@RestController
public class Controller {
	//Initiate imageFilterService
	imageFiltersService imageFilterService = new imageFiltersService();

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	/**
	 * @return {message: a message of status, code: http response code}
	 */
	@GetMapping("/test")
	public ServiceResponse test() {
        return new ServiceResponse("ImageProcessor-service works!!", 200);
    }

	/**
	 * Used to blur an image with the image_processor microservice
	 * 
	 * @return Blurred image {base64 format}
	 */
	@PostMapping("/blur")
	public ServiceResponse blurImage(@RequestBody String imageCode) {
		//System.err.println(imageCode);
		return imageFilterService.blurImage(imageCode);
	}
}
