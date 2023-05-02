package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception;

public class MonitoringNotFoundException extends RuntimeException {

    public MonitoringNotFoundException(String message){
        super(message);
    }

    public MonitoringNotFoundException() {
    }
}
