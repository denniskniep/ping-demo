package com.example.oidcwithping;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class OidcWithPingApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplicationBuilder(OidcWithPingApplication.class).build();
		app.addListeners(new GlobalTruststoreConfig());
		app.addListeners(new BackendProxyConfig());
		app.run(args);
	}
}
