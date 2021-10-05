package com.example.carrentservice.enums;

public enum CarClass {
    ECONOMY("Эконом"), BUISNESS("Бизнес");

    private final String code;

    CarClass(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
