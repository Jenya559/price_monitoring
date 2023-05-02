package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class ProductDublicateException extends RuntimeException {

    public ProductDublicateException(String message) {
        super(message);
    }

    public ProductDublicateException() {
    }
}
