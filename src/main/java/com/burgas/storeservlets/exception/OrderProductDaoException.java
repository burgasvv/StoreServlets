package com.burgas.storeservlets.exception;

public class OrderProductDaoException extends RuntimeException{

    public OrderProductDaoException() {
        super();
    }

    public OrderProductDaoException(String message) {
        super(message);
    }

    public OrderProductDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderProductDaoException(Throwable cause) {
        super(cause);
    }
}
