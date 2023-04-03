package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model;

import lombok.Data;

import javax.persistence.*;



@Data
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name_of_product")
    private String nameOfProduct;


    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

    }



