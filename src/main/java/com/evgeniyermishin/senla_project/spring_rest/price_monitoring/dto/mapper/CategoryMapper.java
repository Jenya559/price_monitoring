package com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.mapper;


import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.dto.CategoryDTO;
import com.evgeniyermishin.senla_project.spring_rest.price_monitoring.model.Category;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryDTO toDto(Category category);
    Category toModel(CategoryDTO categoryDTO);

    List<CategoryDTO> toDto(List<Category> categories);
}
