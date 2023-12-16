package com.epam.esm.exception;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException() {
    }

    public NoSuchEntityException(String messageCode) {
        super(messageCode);
    }

    public NoSuchEntityException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public NoSuchEntityException(Throwable cause) {
        super(cause);
    }
}
