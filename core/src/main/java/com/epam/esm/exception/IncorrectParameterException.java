package com.epam.esm.exception;

import lombok.Getter;

@Getter
public class IncorrectParameterException extends RuntimeException {
    private final ExceptionResult exceptionResult;

    public IncorrectParameterException(ExceptionResult exceptionResult) {
        this.exceptionResult = exceptionResult;
    }

}