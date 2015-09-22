package com.elastic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.fasterxml.jackson.databind.ObjectMapper;

@EnableAutoConfiguration
@Configuration
@ComponentScan
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);

	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
