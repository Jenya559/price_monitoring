package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class ProductShopDuplicateException extends RuntimeException {
    public ProductShopDuplicateException(String message){
        super(message);
    }

    public ProductShopDuplicateException() {
    }
}
