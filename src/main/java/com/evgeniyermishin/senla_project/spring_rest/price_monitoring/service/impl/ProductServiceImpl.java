package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.CategoryRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final ProductMapper productMapper;

    private final CategoryRepository categoryRepository;


    @Override
    public List<ProductDTO> findAllProducts() {
        return productMapper.toDto(productRepository.findAll());
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product maybeProduct = productRepository.findById(id).orElse(null);
        if (maybeProduct == null) {
            log.warn("Продукт с id ({}) не найдена в БД", id);
            throw new ProductNotFoundException("Продукт не найдена в БД");
        }
        return productMapper.toDto(maybeProduct);
    }

    @Override
    public List<ProductDTO> findAllByCategory(String category) {
        List<Product> products = productRepository.getAllByCategory_CategoryName(category);
        return productMapper.toDto(products);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findByCategoryName(productDTO.getCategory());
        Product product = productMapper.toProduct(productDTO);
        product.setCategory(category);
        productRepository.saveAndFlush(product);
        log.info("Продукт ({}) сохранен", product.getNameOfProduct());
        return productMapper.toDto(product);
    }

    @Override
    public ProductDTO editProduct(ProductDTO productDTO) {
        Product maybeProduct = productRepository.findById(productDTO.getId()).orElse(null);
        if (maybeProduct == null) {
            log.warn("Категория с id ({}) не найдена в БД", productDTO.getId());
            throw new ProductNotFoundException("Продукт не найден в БД");
        }
        Product product = productRepository.saveAndFlush(maybeProduct);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteById(Long id) {
        Product maybeProduct = productRepository.findById(id).orElse(null);
        if (maybeProduct == null) {
            log.warn("Продукт с id ({}) не найден в БД", id);
            throw new ProductNotFoundException("Продукт не найден в БД");
        }
        productRepository.deleteById(id);
        log.info("Продукт ({}) удалён", maybeProduct.getNameOfProduct());
    }


}
