package com.payconiq.stock.exception;


public class StockNotFoundException extends RuntimeException {

    public static final String STOCK_NOT_FOUND_MESSAGE = "Stock %d not found";

    public StockNotFoundException(Long id) {
        super(String.format(STOCK_NOT_FOUND_MESSAGE, id));
    }
    public StockNotFoundException() {
        super(STOCK_NOT_FOUND_MESSAGE);
    }
}
