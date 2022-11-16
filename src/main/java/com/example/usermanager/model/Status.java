package com.example.usermanager.model;

public enum Status {
    ACTIVE("Active"),
    INACTIVE("Inactive");

    private String description;

    Status(String description) {
        this.description = description;
    }
}
