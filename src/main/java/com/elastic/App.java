package com.elastic;

import org.elasticsearch.client.Client;
import org.elasticsearch.node.Node;
import org.elasticsearch.node.NodeBuilder;
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
	public Client Client() {
		Node node = NodeBuilder.nodeBuilder().node();
		Client client = node.client();

		return client;
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper();
	}

}
