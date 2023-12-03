package com.epam.esm.exceptions;
/**
 * {@code IncorrectParameterException} is generated in case received parameters have unacceptable value.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public class IncorrectParameterException extends Exception {
    public IncorrectParameterException() {
    }

    public IncorrectParameterException(String messageCode) {
        super(messageCode);
    }

    public IncorrectParameterException(String messageCode, Throwable cause) {
        super(messageCode, cause);
    }

    public IncorrectParameterException(Throwable cause) {
        super(cause);
    }
}
