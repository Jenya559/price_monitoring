package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto;

import lombok.Data;

@Data
public class PeriodDTO {

    private String startDate;

    private String endDate;

    private String nameOfProduct;

    private String shopName;
}
