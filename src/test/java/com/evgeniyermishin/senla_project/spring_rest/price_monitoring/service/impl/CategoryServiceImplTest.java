package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.impl;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper.CategoryMapper;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.repository.CategoryRepository;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category = new Category(1L, "Cars");

    private Category category1 = new Category(2L, "Food");

    @Test
    void addCategoryTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(1L);
        categoryDTO.setCategoryName("Cars");

        when(categoryRepository.findByCategoryName(categoryDTO.getCategoryName())).thenReturn(null);
        when(categoryMapper.toModel(categoryDTO)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);
        categoryService.addCategory(categoryDTO);

        verify(categoryRepository).saveAndFlush(category);
    }

    @Test
    void getAllCategoriesTest() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(category);
        categoryList.add(category1);

        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName(category.getCategoryName());

        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setId(category1.getId());
        categoryDTO1.setCategoryName(category1.getCategoryName());

        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryDTOList.add(categoryDTO);
        categoryDTOList.add(categoryDTO1);

        when(categoryRepository.findAll()).thenReturn(categoryList);
        when(categoryMapper.toDto(categoryList)).thenReturn(categoryDTOList);
        List<CategoryDTO> result = categoryService.getAllCategories();

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void getByIdTest() {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setCategoryName(category.getCategoryName());
        categoryDTO.setId(category.getId());

        when(categoryRepository.findById(category.getId())).thenReturn(Optional.ofNullable(category));
        when(categoryMapper.toDto(category)).thenReturn(categoryDTO);
        CategoryDTO result = categoryService.getById(1L);

        assertNotNull(result);
        assertEquals("Cars", result.getCategoryName());

    }

    @Test
    void deleteByIdTest() {
        Long id = 123L;
        categoryService.deleteById(id);

        verify(categoryRepository).deleteById(id);
    }

    @Test
    void editTest(){
        CategoryDTO categoryDTO=new CategoryDTO();
        categoryDTO.setId(category.getId());
        categoryDTO.setCategoryName("Flowers");
        Category category2=new Category(categoryDTO.getId(),categoryDTO.getCategoryName());

        when(categoryRepository.findById(categoryDTO.getId())).thenReturn(Optional.ofNullable(category));
        when(categoryMapper.toModel(categoryDTO)).thenReturn(category2);
        when(categoryMapper.toDto(category2)).thenReturn(categoryDTO);
        categoryService.edit(categoryDTO);

        verify(categoryRepository).saveAndFlush(category2);

    }
}
