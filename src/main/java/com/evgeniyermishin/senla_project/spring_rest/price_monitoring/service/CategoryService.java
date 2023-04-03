package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;

import java.util.List;

public interface CategoryService {
    CategoryDTO addCategory(CategoryDTO categoryDTO);

    List<CategoryDTO> getAllCategories();

    CategoryDTO getById(Long id);

    void deleteById(Long id);

    CategoryDTO edit(CategoryDTO categoryDTO);
}
