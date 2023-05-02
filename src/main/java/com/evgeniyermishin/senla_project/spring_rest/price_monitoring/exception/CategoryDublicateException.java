package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class CategoryDublicateException extends RuntimeException {

    public CategoryDublicateException(String message) {
        super(message);
    }

    public CategoryDublicateException() {
    }
}
