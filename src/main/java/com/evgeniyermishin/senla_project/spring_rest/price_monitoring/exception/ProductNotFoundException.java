package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ProductNotFoundException  extends RuntimeException {

    public ProductNotFoundException(String message) {
        super(message);
    }
}
