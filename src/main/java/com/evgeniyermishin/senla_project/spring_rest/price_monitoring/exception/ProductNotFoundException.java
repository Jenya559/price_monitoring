package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class ProductNotFoundException  extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }

    public ProductNotFoundException() {
    }
}
