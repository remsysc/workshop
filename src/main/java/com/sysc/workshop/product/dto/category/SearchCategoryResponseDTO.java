package com.sysc.workshop.product.dto.category;

import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import lombok.Data;

@Data
//evolve the DTO to include filters or HATEOAS.
// add boilerplate code for the DTO
public class SearchCategoryResponseDTO {

    private PagedResponseDTO<CategoryDto> pageInfo;

    public SearchCategoryResponseDTO(PagedResponseDTO<CategoryDto> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
