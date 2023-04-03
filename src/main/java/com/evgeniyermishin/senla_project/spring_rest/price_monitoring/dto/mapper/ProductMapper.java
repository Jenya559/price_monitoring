package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {CategoryMapper.class})
public interface ProductMapper {

    @Mapping(source = "category.categoryName", target = "category")
    ProductDTO toDto(Product product);

    @Mapping(source = "category", target = "category.categoryName")
    Product toProduct(ProductDTO productDTO);

    @Mapping(source = "category.categoryName", target = "category")
    List<ProductDTO> toDto(List<Product> products);

}
