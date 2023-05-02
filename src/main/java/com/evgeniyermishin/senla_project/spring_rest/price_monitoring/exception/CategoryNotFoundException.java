package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class CategoryNotFoundException  extends RuntimeException {

    public CategoryNotFoundException(String message) {
        super(message);
    }

    public CategoryNotFoundException() {
    }
}
