package com.optimal.api.customExceptions;

public class RandomUserServiceException extends RuntimeException {
    public RandomUserServiceException(String message) {
        super(message);
    }

    public RandomUserServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
