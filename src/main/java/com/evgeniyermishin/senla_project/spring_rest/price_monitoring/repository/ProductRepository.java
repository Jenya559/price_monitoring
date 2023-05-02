package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

List<Product>getAllByCategory_CategoryName(String category);
Product findByNameOfProduct(String nameOfProduct);



}
