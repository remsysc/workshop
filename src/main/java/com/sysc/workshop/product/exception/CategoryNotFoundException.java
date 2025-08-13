package com.sysc.workshop.product.exception;

import com.sysc.workshop.core.exception.ResourceNotFound;

public class CategoryNotFoundException extends ResourceNotFound {

    public CategoryNotFoundException(String id) {
        super("Category not found: " + id);
    }
}
