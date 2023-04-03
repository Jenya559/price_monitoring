package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model;


import lombok.Data;

import javax.persistence.*;


@Data
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_of_category")
    private String categoryName;

}
