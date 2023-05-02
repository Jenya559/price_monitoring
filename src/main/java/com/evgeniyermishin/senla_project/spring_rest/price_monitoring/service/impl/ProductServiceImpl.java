package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.CategoryNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductDublicateException;
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
        List<ProductDTO> list = productMapper.toDto(productRepository.findAll());
        return list;
    }

    @Override
    public ProductDTO getProductById(Long id) {
        Product maybeProduct = productRepository.findById(id).orElse(null);
        if (maybeProduct == null) {
            log.warn("Продукт с id ({}) не найден в БД", id);
            throw new ProductNotFoundException();
        }
        return productMapper.toDto(maybeProduct);
    }

    @Override
    public List<ProductDTO> findAllByCategory(String category) {
        List<Product> maybeProducts = productRepository.getAllByCategory_CategoryName(category);
        Category maybeCategory = categoryRepository.findByCategoryName(category);
        if (maybeCategory == null) {
            log.warn("Данная категория ({}) не найдена в БД", category);
            throw new CategoryNotFoundException();
        }
        return productMapper.toDto(maybeProducts);
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        Category category = categoryRepository.findByCategoryName(productDTO.getCategory());
        Product maybeProduct = productRepository.findByNameOfProduct(productDTO.getNameOfProduct());
        if (maybeProduct != null) {
            throw new ProductDublicateException();
        }
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
            log.warn("Продукт с id ({}) не найден в БД", productDTO.getId());
            throw new ProductNotFoundException();
        }
        Category maybeCategory = categoryRepository.findByCategoryName(productDTO.getCategory());
        if (maybeCategory == null) {
            log.warn("Категория с названием ({}) не найдена в БД", productDTO.getCategory());
            throw new CategoryNotFoundException();
        }
        Product product = productMapper.toProduct(productDTO);
        product.setCategory(maybeCategory);
        productRepository.saveAndFlush(product);
        return productMapper.toDto(product);
    }

    @Override
    public void deleteById(Long id) {
        productRepository.deleteById(id);
        log.info("Продукт c id ({}) удалён", id);
    }

    @Override
    public void saveAll(List<Product>list) {
        productRepository.saveAllAndFlush(list);
    }


}
