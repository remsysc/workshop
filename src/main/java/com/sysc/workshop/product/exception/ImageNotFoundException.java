package com.sysc.workshop.product.exception;

import com.sysc.workshop.core.exception.ResourceNotFound;

public class ImageNotFoundException extends ResourceNotFound {

    public ImageNotFoundException(String id) {
        super("Image not found: " + id);
    }
}
