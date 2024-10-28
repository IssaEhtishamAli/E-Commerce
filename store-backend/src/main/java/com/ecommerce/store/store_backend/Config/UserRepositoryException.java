package com.ecommerce.store.store_backend.Config;

public class UserRepositoryException extends RuntimeException{
    public UserRepositoryException(String message) {
        super(message);
    }

    public UserRepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
