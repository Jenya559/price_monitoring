package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.controller;

import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.service.CategoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Api(tags = "category-rest-controller")
@RequestMapping("/api")
public class CategoryController {


    final CategoryService categoryService;

    @PostMapping("/category")
    @ApiOperation(value = "Добавление категории продуктов")
    public CategoryDTO saveCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.addCategory(categoryDTO);

    }

    @GetMapping("/category")
    @ApiOperation(value = "Получение всех категорий продуктов")
    public List<CategoryDTO> getAllCategories() {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        return categories;
    }

    @GetMapping("/category/{id}")
    @ApiOperation(value = "Получение категории по id")
    public CategoryDTO getCategoryById(@PathVariable Long id) {
        return categoryService.getById(id);
    }

    @DeleteMapping("/category/id/{id}")
    @ApiOperation(value = "Удаление категории по id")
    public String deleteCategory(@PathVariable Long id) {
        categoryService.deleteById(id);
        return "Категория с id=" + id + " удалена";

    }

    @PutMapping("/category")
    @ApiOperation(value = "Изменение категории")
    public CategoryDTO editCategory(@RequestBody CategoryDTO categoryDTO) {
        return categoryService.edit(categoryDTO);
    }

}