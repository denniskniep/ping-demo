package com.example.resourceserver;

import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class GlobalTruststoreConfig implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        String trustStorePath = environment.getProperty("global.clients.ssl.trustStorePath");
        String trustStorePassword = environment.getProperty("global.clients.ssl.trustStorePassword");

        Resource resource = event.getApplicationContext().getResource(trustStorePath);
        String absoluteTrustStorePath;
        try {
            absoluteTrustStorePath = resource.getFile().getAbsolutePath();
        } catch (IOException e) {
           throw new RuntimeException("File for resource not found!");
        }

        System.setProperty("javax.net.ssl.trustStoreType", "jks");
        System.setProperty("javax.net.ssl.trustStore", absoluteTrustStorePath);
        System.setProperty("javax.net.ssl.trustStorePassword", trustStorePassword);
    }
}
