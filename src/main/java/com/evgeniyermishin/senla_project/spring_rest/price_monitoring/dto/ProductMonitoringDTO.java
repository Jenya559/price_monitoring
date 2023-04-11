package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductMonitoringDTO {

    private Long id;

    private double price;

    private String product;

    private String shop;

    private LocalDateTime localDateTime;

}
