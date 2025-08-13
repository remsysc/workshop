package com.sysc.workshop.product.exception;

import com.sysc.workshop.core.exception.AlreadyExistsException;

public class CategoryAlreadyExists extends AlreadyExistsException {

    public CategoryAlreadyExists(String id) {
        super("Category: " + id + " already exists.");
    }
}
