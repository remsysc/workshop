package com.sysc.workshop.product.exception;

import com.sysc.workshop.core.exception.ResourceNotFound;

public class ProductNotFoundException extends ResourceNotFound {

    public ProductNotFoundException(String id) {
        super("Product/s not found: " + id);
    }
}
