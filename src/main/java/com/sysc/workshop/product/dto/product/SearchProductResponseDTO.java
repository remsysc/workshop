package com.sysc.workshop.product.dto.product;

import com.sysc.workshop.product.dto.common.PagedResponseDTO;
import lombok.Data;

@Data
public class SearchProductResponseDTO {

    private PagedResponseDTO<ProductDto> pageInfo;

    public SearchProductResponseDTO(PagedResponseDTO<ProductDto> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
