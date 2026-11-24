package com.inventory.api.exception;

public class OutOfStockException extends RuntimeException {
    public OutOfStockException(String productName, int available) {
        super("Not enough stock for " + productName + ". Available: " + available);
    }
}
