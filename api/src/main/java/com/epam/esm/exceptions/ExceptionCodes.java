package com.epam.esm.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public enum ExceptionCodes {
    BAD_REQUEST_EXCEPTION(40001, "BAD_REQUEST"),
    NOT_FOUND_EXCEPTION(40401, "NOT_FOUND"),
    METHOD_NOT_ALLOWED_EXCEPTION(40501, "METHOD_NOT_ALLOWED"),
    INTERNAL_SERVER_ERROR_EXCEPTION(50001, "INTERNAL_SERVER_ERROR");

    private final int code;
    private final String message;


}
