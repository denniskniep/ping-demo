package com.example.resourceserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class ResourceServer {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplicationBuilder(ResourceServer.class).build();
		app.addListeners(new GlobalTruststoreConfig());
		app.addListeners(new BackendProxyConfig());
		app.run(args);
	}
}
