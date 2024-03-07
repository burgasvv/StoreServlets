package com.burgas.storeservlets.exception;

public class OrderDaoException extends RuntimeException{

    public OrderDaoException() {
        super();
    }

    public OrderDaoException(String message) {
        super(message);
    }

    public OrderDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDaoException(Throwable cause) {
        super(cause);
    }
}
