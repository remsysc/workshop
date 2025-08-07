package com.sysc.workshop.product.mapper;

import com.sysc.workshop.product.dto.category.CategoryDto;
import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import com.sysc.workshop.product.model.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = CategoryMapper.class)
public interface PagedResponseMapper {
    PagedResponseDTO<CategoryDto> toDto(
        PagedResponseDTO<Category> pagedResponseDTO
    );
}
