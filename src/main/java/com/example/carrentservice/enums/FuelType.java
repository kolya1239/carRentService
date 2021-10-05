package com.example.carrentservice.enums;

public enum FuelType {
    GAS("Бензин"), DIESEL("Дизель");

    private final String code;

    private FuelType(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
