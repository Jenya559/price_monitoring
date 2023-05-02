package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.ProductDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.ProductMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = ProductController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    private ObjectMapper mapper = new ObjectMapper();
    @MockBean
    private ProductMapper productMapper;

    @Test
    public void testGetAllProducts() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setNameOfProduct("BMW");
        productDTO.setCategory("Cars");

        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setId(2L);
        productDTO1.setNameOfProduct("Milk");
        productDTO1.setCategory("Food");

        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO);
        productDTOList.add(productDTO1);
        Mockito.when(productService.findAllProducts()).thenReturn(productDTOList);
        mockMvc.perform(get("/api/v1/product")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].nameOfProduct", Matchers.equalTo("BMW")));
    }

    @Test
    public void testGetProduct() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setNameOfProduct("BMW");
        productDTO.setCategory("Cars");
        Mockito.when(productService.getProductById(1L)).thenReturn(productDTO);
        mockMvc.perform(get("/api/v1/product/{id}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.nameOfProduct", Matchers.equalTo("BMW")));
    }

    @Test
    public void testGetProductByCategory() throws Exception {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(1L);
        productDTO.setNameOfProduct("BMW");
        productDTO.setCategory("Cars");
        ProductDTO productDTO1 = new ProductDTO();
        productDTO1.setNameOfProduct("Milk");
        productDTO1.setId(2L);
        productDTO1.setCategory("Food");
        List<ProductDTO> productDTOList = new ArrayList<>();
        productDTOList.add(productDTO);
        productDTOList.add(productDTO1);
        Mockito.when(productService.findAllByCategory("Food")).thenReturn(productDTOList.stream().filter(p -> p.getCategory() == "Food").collect(Collectors.toList()));
        mockMvc.perform(get("/api/v1/product/filter").param("category","Food")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(1)))
                .andExpect(jsonPath("$[0].nameOfProduct", Matchers.equalTo("Milk")));

    }
}