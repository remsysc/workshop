package com.sysc.workshop.core.exception;

public class InventoryLimitExceededException extends RuntimeException {
    public InventoryLimitExceededException(String message) {
        super(message);
    }
}
