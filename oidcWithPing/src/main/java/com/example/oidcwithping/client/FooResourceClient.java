package com.example.oidcwithping.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.RequestScope;

@Service
@RequestScope
public class FooResourceClient extends OAuthClient {

    private final static String URL = "http://localhost:8282/api";

    @Autowired
    public FooResourceClient(String accessToken,String proxyHost, Integer proxyPort) {
        super(accessToken, proxyHost, proxyPort);
    }

    public FooModel getNormalFoo() {
        return restTemplate.getForObject(URL + "/foo/normal", FooModel.class);
    }
}
