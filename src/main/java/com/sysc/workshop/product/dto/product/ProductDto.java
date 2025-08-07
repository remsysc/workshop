package com.sysc.workshop.product.dto.product;

import com.sysc.workshop.product.dto.image.ImageDto;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import lombok.Data;

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
