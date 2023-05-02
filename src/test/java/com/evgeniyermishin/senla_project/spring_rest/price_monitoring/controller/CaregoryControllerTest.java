package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = CategoryController.class,
        excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class CaregoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper = new ObjectMapper();

    @MockBean
    CategoryServiceImpl categoryService;

    @Test
    public void testSaveCategory() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setCategoryName("Cars");
        Mockito.when(categoryService.addCategory(ArgumentMatchers.any())).thenReturn(categoryDTO);
        String json = mapper.writeValueAsString(categoryDTO);
        mockMvc.perform(post("/api/v1/category").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }

    @Test
    public void testGetAllCategories() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setCategoryName("Cars");

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(2L);
        categoryDTO1.setCategoryName("Food");

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(categoryDTO);
        categoryDTOList.add(categoryDTO1);
        Mockito.when(categoryService.getAllCategories()).thenReturn(categoryDTOList);
        mockMvc.perform(get("/api/v1/category")).andExpect(status().isOk()).andExpect(jsonPath("$", Matchers.hasSize(2)))
                .andExpect(jsonPath("$[0].categoryName", Matchers.equalTo("Cars")));
    }

    @Test
    public void testGetById() throws Exception {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setCategoryName("Cars");
        Mockito.when(categoryService.getById(1L)).thenReturn(categoryDTO);
        mockMvc.perform(get("/api/v1/category/{id}", 1L)).andExpect(status().isOk())
                .andExpect(jsonPath("$.categoryName", Matchers.equalTo("Cars")));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/category/{id}", 10001L))
                .andExpect(status().isOk());
    }

    @Test
    public void testEditCategory() throws Exception{
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(2L);
        categoryDTO.setCategoryName("Food");
        Mockito.when(categoryService.edit(ArgumentMatchers.any())).thenReturn(categoryDTO);
        String json = mapper.writeValueAsString(categoryDTO);
        mockMvc.perform(put("/api/v1/category").contentType(MediaType.APPLICATION_JSON).characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
    }
}
