package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class ShopNotFoundException extends RuntimeException {

    public ShopNotFoundException(String message) {
        super(message);
    }

    public ShopNotFoundException() {
    }
}
