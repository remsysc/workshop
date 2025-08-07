package com.sysc.workshop.product.dto.category;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SearchCategoryRequestDTO {

    @NotBlank
    private String category;

    @Min(0)
    private int page = 0;

    @Min(1)
    private int size = 10;
}
