package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model;

import com.opencsv.bean.CsvBindByName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;



@Data
@Entity
@Table(name = "products")
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @CsvBindByName(column = "id",required = true)
    private Long id;


    @Column(name = "name_of_product",unique = true)
    private String nameOfProduct;


    @ManyToOne()
    @JoinColumn(name = "category_id")
    private Category category;

}



