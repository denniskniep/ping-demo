package com.example.resourceserver;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.ConfigurableEnvironment;


public class BackendProxyConfig implements ApplicationListener<ApplicationPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationPreparedEvent event) {
        ConfigurableEnvironment environment = event.getApplicationContext().getEnvironment();

        String host = environment.getProperty("proxy.host");
        String port = environment.getProperty("proxy.port");
        if(!StringUtils.isEmpty(host) && !StringUtils.isEmpty(port)){
            System.setProperty("http.proxyHost", host);
            System.setProperty("http.proxyPort", port);

            System.setProperty("https.proxyHost", host);
            System.setProperty("https.proxyPort", port);
        }
    }
}
