package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model;

import lombok.Data;

import javax.persistence.*;
@Data
@Entity
@Table(name = "shops")
public class Shop {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_of_shop")
    private String shopName;




}

