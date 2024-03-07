package com.burgas.storeservlets.exception;

public class ProductDaoException extends RuntimeException{

    public ProductDaoException() {
        super();
    }

    public ProductDaoException(String message) {
        super(message);
    }

    public ProductDaoException(String message, Throwable cause) {
        super(message, cause);
    }

    public ProductDaoException(Throwable cause) {
        super(cause);
    }
}
