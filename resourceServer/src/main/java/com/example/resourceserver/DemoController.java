package com.example.resourceserver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

@RestController
public class DemoController {

    @GetMapping("/api/foo/normal")
    public FooModel normal() {
        return new FooModel( randomAlphabetic(4) + "-123",  "Foo-" + randomAlphabetic(4));
    }

    @GetMapping("/api/foo/secret")
    public FooModel secret() {
        return new FooModel( randomAlphabetic(4) + "-456",  "Bar-" + randomAlphabetic(4));
    }
}
