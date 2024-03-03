package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.boot.web.server.ConfigurableWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		//Starts a spring application
		SpringApplication.run(Application.class, args);
	}

	@Bean
    public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
		//Sets the port number of the application
		
		//TODO: set port in environment variables
        return factory -> factory.setPort(8080); // Set your desired port here
    }
}