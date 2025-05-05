package com.example.javatutorial.enums;

import lombok.Getter;

@Getter
public enum NorthwindConstants {

    DESTINATION_NAME("Northwind"),
    SERVICE_PATH("/V4/Northwind/Northwind.svc/");

    private final String value;

    NorthwindConstants(String value) {
        this.value = value;
    }
}
