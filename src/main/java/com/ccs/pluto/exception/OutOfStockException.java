package com.ccs.pluto.exception;

public class OutOfStockException extends RuntimeException {

    String itemName;
    int stockNumber;

    public OutOfStockException(String itemName, int stockNumber) {
        this.itemName = itemName;
        this.stockNumber = stockNumber;
    }
}