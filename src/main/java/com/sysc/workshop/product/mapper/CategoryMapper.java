package com.sysc.workshop.product.mapper;

import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.model.Category;
import java.util.List;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    List<CategoryDto> toDtoList(List<Category> categories);
    CategoryDto toDto(Category category);
}
