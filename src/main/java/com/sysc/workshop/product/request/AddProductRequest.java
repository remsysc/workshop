package com.sysc.workshop.product.request;

import com.sysc.workshop.product.model.Category;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddProductRequest {

    private String brand;
    private String name;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
