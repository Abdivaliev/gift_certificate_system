package com.epam.esm.exception;

public class ExistingEntityException extends RuntimeException {

    public ExistingEntityException(String messageCode) {
        super(messageCode);
    }

}
