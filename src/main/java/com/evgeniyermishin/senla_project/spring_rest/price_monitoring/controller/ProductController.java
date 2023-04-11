package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.CategoryService;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductService;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.utils.CsvUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "product-rest-controller")
@RequestMapping("/api/v1/product")
public class ProductController {


    private final ProductMapper productMapper;

    private final ProductRepository productRepository;

    private final ProductService productService;

    private final CategoryService categoryService;


    @GetMapping
    @ApiOperation(value = "Получение всех продуктов")
    public List<ProductDTO> getAllProducts() {
        return productService.findAllProducts();

    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Получение продукта по id")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/filterByCategory/{category}")
    @ApiOperation(value = "Получение продуктов по категории")
    public List<ProductDTO> getProductByCategory(@PathVariable String category) {
        return productService.findAllByCategory(category);
    }

    @PostMapping
    @ApiOperation(value = "Добавление продуктов")
    public ProductDTO addProduct(@RequestBody ProductDTO productDTO) {
        return productService.addProduct(productDTO);
    }

    @PutMapping
    @ApiOperation(value = "Изменение продукта")
    public ProductDTO editProduct(@RequestBody ProductDTO productDTO) {
        return productService.editProduct(productDTO);
    }

    @DeleteMapping("/products/{id}")
    @ApiOperation(value = "Удаление продукта по id")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping(value = "/upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        productRepository.saveAll(CsvUtils.read(Product.class, file.getInputStream()));
    }

}

