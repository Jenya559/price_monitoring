package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.CategoryMapper;
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
    final CategoryRepository categoryRepository;

    final CategoryMapper categoryMapper;

    @Override
    public CategoryDTO addCategory(CategoryDTO categoryDTO) {
        Category category = categoryRepository.saveAndFlush(categoryMapper.toModel(categoryDTO));
        log.info("Категория " + category.getCategoryName() + " сохранена");
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return categoryMapper.toDto(categoryRepository.findAll());
    }

    @Override
    public CategoryDTO getById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            log.warn("Категории с данным id не существует");
            throw new CategoryNotFoundException("Категории с данным id не существует");

        }
        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        if (category == null) {
            log.warn("Категории с данным id не существует");
            throw new CategoryNotFoundException("Категории с данным id не существует");
        }
        categoryRepository.deleteById(id);
        log.info("Продукт удалён");
    }

    @Override
    public CategoryDTO edit(CategoryDTO categoryDTO) {
        Category maybeCategory = categoryRepository.findById(categoryDTO.getId()).orElse(null);
        if (maybeCategory == null) {
            log.warn("Данное категории нет в базе");
            throw new CategoryNotFoundException("Данное категории нет в базе");
        }
        Category editCategory = categoryRepository.saveAndFlush(categoryMapper.toModel(categoryDTO));
        log.info("Категория изменена");
        return categoryMapper.toDto(editCategory);
    }

}

