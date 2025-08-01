package com.sysc.workshop.product.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

// information returning back to client
@Data
public class ProductDto {
    private UUID id;
    private String brand;
    private String name;
    private BigDecimal price;
    private int inventory;
    private String categoryName;
    private List<ImageDto> images;
}
