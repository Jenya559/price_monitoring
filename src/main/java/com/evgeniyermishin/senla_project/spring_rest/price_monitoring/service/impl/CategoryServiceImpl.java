package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.CategoryMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.CategoryDublicateException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.exception.CategoryNotFoundException;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.CategoryRepository;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category maybeCategory = categoryRepository.findByCategoryName(categoryDTO.getCategoryName());
        if (maybeCategory != null) {
            throw new CategoryDublicateException();
        }
        Category category = categoryMapper.toModel(categoryDTO);
        categoryRepository.saveAndFlush(category);
        log.info("Категория ({}) сохранена", category.getCategoryName());
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> list = categoryMapper.toDto(categoryRepository.findAll());
        return list;
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            log.warn("Категория с id ({}) не найдена в БД", id);
            throw new CategoryNotFoundException();
        }
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
        log.info("Категория удалена");
    }

    @Override
    public CategoryDTO edit(CategoryDTO categoryDTO) {
        Category maybeCategory = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        if (maybeCategory == null) {
            log.warn("Категория с id ({}) не найдена в БД", categoryDTO.getId());
            throw new CategoryNotFoundException();
        }
        log.info("Категория ({}) изменена на ({})", maybeCategory.getCategoryName(), categoryDTO.getCategoryName());
        Category editCategory = categoryRepository.saveAndFlush(categoryMapper.toModel(categoryDTO));
        return categoryMapper.toDto(editCategory);
    }

}

