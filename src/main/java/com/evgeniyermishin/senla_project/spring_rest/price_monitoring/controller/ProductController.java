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
@RequestMapping("/api")
public class ProductController {


    final ProductMapper productMapper;

    final ProductRepository productRepository;

    final  ProductService productService;

    final CategoryService categoryService;


    @GetMapping("/products")
    @ApiOperation(value = "Получение всех продуктов")
    public List<ProductDTO> getAllProducts() {
        return productService.findAllProducts();

    }

    @GetMapping("/products/{id}")
    @ApiOperation(value = "Получение продукта по id")
    public ProductDTO getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/filterByCategory/{category}")
    @ApiOperation(value = "Получение продукта по категории")
    public List<ProductDTO> getProductByCategory(@PathVariable String category) {
        return productService.findAllByCategory(category);
    }

    @PostMapping("/products")
    @ApiOperation(value = "Добавление продуктов")
    public ProductDTO saveProduct(@RequestBody ProductDTO productDTO) {
     return   productService.addProduct(productDTO);
    }

    @PutMapping("/products")
    @ApiOperation(value = "Изменение продукта")
    public ProductDTO editProduct(@RequestBody ProductDTO productDTO) {
      return  productService.editProduct(productDTO);
    }

    @DeleteMapping("/products/{id}")
    @ApiOperation(value = "Удаление продукта по id")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return "Продукт с id=" + id + " удалён";
    }

    @PostMapping(value = "/upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        productRepository.saveAll(CsvUtils.read(Product.class, file.getInputStream()));
    }

}

