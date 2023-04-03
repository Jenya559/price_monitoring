package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;

import java.util.List;

public interface ProductService {

    List<ProductDTO> findAllProducts();

    ProductDTO getProductById(Long id);

    List<ProductDTO> findAllByCategory(String category);

    ProductDTO addProduct(ProductDTO productDTO);

    ProductDTO editProduct(ProductDTO productDTO);

    void deleteById(Long id);


}

