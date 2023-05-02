package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.CategoryNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.ProductNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.params.shadow.com.univocity.parsers.common.record.Record;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParser;
import org.junit.jupiter.params.shadow.com.univocity.parsers.csv.CsvParserSettings;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "product-rest-controller")
@RequestMapping("/api/v1/product")
public class ProductController {


    private final ProductMapper productMapper;

    private final ProductService productService;



    @GetMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение всех продуктов")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productsDTO = productService.findAllProducts();
        return new ResponseEntity<>(productsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение продукта по id")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        try {
            ProductDTO productDTO = productService.getProductById(id);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Продукта с id " + id + " нет в БД", HttpStatus.OK);
        }
    }

    @GetMapping("/filter")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    @ApiOperation(value = "Получение продуктов по категории")
    public ResponseEntity<?> getProductByCategory(@RequestParam String category) {
        try {
            List<ProductDTO> productDTOList = productService.findAllByCategory(category);
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Категория " + category + " не найдена в БД", HttpStatus.OK);
        }
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Добавление продуктов")
    public ResponseEntity<?> addProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO productDTO1 = productService.addProduct(productDTO);
            return new ResponseEntity<>(productDTO1, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Продукт " + productDTO.getNameOfProduct() + " уже есть в БД", HttpStatus.OK);
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    @ApiOperation(value = "Изменение продукта")
    public ResponseEntity<?> editProduct(@RequestBody ProductDTO productDTO) {
        try {
            ProductDTO productDTO1 = productService.editProduct(productDTO);
            return new ResponseEntity<>(productDTO1, HttpStatus.OK);
        } catch (ProductNotFoundException e) {
            return new ResponseEntity<>("Продукт с id " + productDTO.getId() + " не найден в БД", HttpStatus.OK);

        } catch (CategoryNotFoundException e) {
            return new ResponseEntity<>("Категория " + productDTO.getCategory() + " не найдена в БД", HttpStatus.OK);
        }

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ApiOperation(value = "Удаление продукта по id")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
    }

    @PostMapping(value = "/upload", produces = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiOperation(value = "Загрузка файла")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<?> uploadMultipart(@RequestParam("file") MultipartFile file) throws IOException {
        InputStream inputStream = file.getInputStream();
        CsvParserSettings settings = new CsvParserSettings();
        settings.setHeaderExtractionEnabled(true);
        settings.detectFormatAutomatically();
        CsvParser parser = new CsvParser(settings);
        List<Record> parseAllRecords = parser.parseAllRecords(inputStream);
        parseAllRecords.forEach(record -> {
            Product product = new Product();
            Category category = new Category();
            category.setCategoryName(record.getString("category"));
            product.setNameOfProduct(record.getString("productName"));
            product.setCategory(category);
            productService.addProduct(productMapper.toDto(product));
        });
        return new ResponseEntity<>(HttpStatus.OK);
    }
}


