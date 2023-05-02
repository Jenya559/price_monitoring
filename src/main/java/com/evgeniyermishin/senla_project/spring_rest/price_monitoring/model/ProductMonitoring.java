package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Data
@Entity
@Table(name = "products_monitoring")
public class ProductMonitoring {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "price")
    private double price;

    @OneToOne()
    @JoinColumn(name = "product_id")
    private Product product;

    @OneToOne()
    @JoinColumn(name = "shop_id")
    private Shop shop;

    @Column(name = "make_date")
    @CreationTimestamp
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime localDateTime;

    public ProductMonitoring(Long id, double price, Product product, Shop shop) {
        this.id = id;
        this.price = price;
        this.product = product;
        this.shop = shop;
    }

    public ProductMonitoring(Product product, Shop shop, LocalDateTime localDateTime) {
        this.product = product;
        this.shop = shop;
        this.localDateTime = localDateTime;
    }
}
