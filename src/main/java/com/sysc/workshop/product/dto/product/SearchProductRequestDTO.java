package com.sysc.workshop.product.dto.product;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class SearchProductRequestDTO {

    String name;
    String brand;
    String category;

    @Min(1)
    Integer size = 10;

    @Min(0)
    Integer page = 0;
}
