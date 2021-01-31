package com.example.resourceserver;

public class FooModel {

    private String id;
    private String name;

    public FooModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
