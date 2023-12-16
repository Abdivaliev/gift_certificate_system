package com.epam.esm.exception;

public class DuplicateEntityException extends RuntimeException {
    public DuplicateEntityException() {
    }

    public DuplicateEntityException(String messageCode) {
        super(messageCode);
    }

    public DuplicateEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public DuplicateEntityException(Throwable cause) {
        super(cause);
    }
}
