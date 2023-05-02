package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Product;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.CategoryRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    ProductMapper productMapper;

    @InjectMocks
    private ProductServiceImpl productService;

    private String categoryName = "Cars";
    private String categoryName1 = "Food";
    private Category category = new Category(1L, categoryName);
    private Category category1 = new Category(2L, categoryName1);
    private Product product = new Product(1L, "BMW", category);
    private Product product1 = new Product(2L, "Milk", category1);
    private Product product2 = new Product(3L, "KIA", category);
    private ProductDTO productDTO = new ProductDTO();
    private ProductDTO productDTO1 = new ProductDTO();

    @Test
    void testFindAllByCategory() {
        List<Product> products = new ArrayList();
        products.add(product);
        products.add(product1);

        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTO.setId(product.getId());
        productDTO.setCategory(categoryName);
        productDTO.setNameOfProduct(product.getNameOfProduct());

        productDTO1.setId(product1.getId());
        productDTO1.setCategory(categoryName1);
        productDTO1.setNameOfProduct(product1.getNameOfProduct());
        productDTOS.add(productDTO);
        productDTOS.add(productDTO1);

        List<Product> productsFilter = products.stream().filter(p -> p.getCategory().getCategoryName().equals(categoryName)).collect(Collectors.toList());
        List<ProductDTO> productDTOFilter = productDTOS.stream().filter(p -> p.getCategory().equals(categoryName)).collect(Collectors.toList());

        when(categoryRepository.findByCategoryName(categoryName)).thenReturn(category);
        when(productRepository.getAllByCategory_CategoryName(categoryName)).thenReturn(productsFilter);
        when(productMapper.toDto(productsFilter)).thenReturn(productDTOFilter);
        List<ProductDTO> result = productService.findAllByCategory(categoryName);

        assertNotNull(productsFilter);
        assertEquals(1, result.size());
    }

    @Test
    void testFindAllProducts() {

        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product2);

        productDTO.setId(product.getId());
        productDTO.setNameOfProduct(product.getNameOfProduct());
        productDTO.setCategory(category.getCategoryName());

        productDTO1.setId(product2.getId());
        productDTO1.setCategory(category.getCategoryName());
        productDTO1.setNameOfProduct(product2.getNameOfProduct());
        List<ProductDTO> productDTOS = new ArrayList<>();
        productDTOS.add(productDTO);
        productDTOS.add(productDTO1);

        when(productRepository.findAll()).thenReturn(products);
        when(productMapper.toDto(products)).thenReturn(productDTOS);
        List<ProductDTO> result = productService.findAllProducts();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getProductByIdTest() {
        productDTO.setNameOfProduct(product.getNameOfProduct());
        productDTO.setCategory(category.getCategoryName());
        productDTO.setId(1L);

        when(productRepository.findById(product.getId())).thenReturn(Optional.of(product));
        when(productMapper.toDto(product)).thenReturn(productDTO);
        ProductDTO result = productService.getProductById(1L);

        assertAll(
                () -> assertEquals("Cars", result.getCategory()),
                () -> assertEquals("BMW", result.getNameOfProduct()));

    }

    @Test
    void addProductTest() {
        productDTO.setId(product.getId());
        productDTO.setCategory(category.getCategoryName());
        productDTO.setNameOfProduct(product.getNameOfProduct());

        when(categoryRepository.findByCategoryName(productDTO.getCategory())).thenReturn(category);
        when(productRepository.findByNameOfProduct(productDTO.getNameOfProduct())).thenReturn(null);
        when(productMapper.toProduct(productDTO)).thenReturn(product);
        when(productRepository.saveAndFlush(any(Product.class))).thenReturn(product);
        when(productMapper.toDto(product)).thenReturn(productDTO);
        productService.addProduct(productDTO);

        verify(productRepository).saveAndFlush(any(Product.class));

    }

    @Test
    void editProductTest() {
        productDTO.setId(product.getId());
        productDTO.setCategory("Cars");
        productDTO.setNameOfProduct("BMW M5");
        Product product3 = new Product(productDTO.getId(), productDTO.getNameOfProduct(), product.getCategory());

        when(productRepository.findById(productDTO.getId())).thenReturn(Optional.ofNullable(product));
        when(categoryRepository.findByCategoryName(productDTO.getCategory())).thenReturn(category);
        when(productMapper.toProduct(productDTO)).thenReturn(product3);
        when(productMapper.toDto(product3)).thenReturn(productDTO);
        productService.editProduct(productDTO);

        verify(productRepository).saveAndFlush(product3);
    }


    @Test
    void saveAllTest() {
        List<Product> products = new ArrayList<>();
        products.add(product);
        products.add(product1);

        when(productRepository.saveAllAndFlush(any(List.class))).thenReturn(products);
        productService.saveAll(products);

        verify(productRepository).saveAllAndFlush(products);

    }

    @Test
    void deleteByIdTest() {
        Long id = 123L;
        productService.deleteById(id);
        verify(productRepository).deleteById(id);
    }
}
