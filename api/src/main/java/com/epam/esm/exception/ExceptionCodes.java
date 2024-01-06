package com.epam.esm.exception;


import lombok.Getter;

@Getter
public enum ExceptionCodes {
    BAD_REQUEST_EXCEPTION(40001, "BAD_REQUEST"),
    NOT_FOUND_EXCEPTION(40401, "NOT_FOUND"),
    METHOD_NOT_ALLOWED_EXCEPTION(40501, "METHOD_NOT_ALLOWED"),
    CONFLICT_EXCEPTION(40901, "CONFLICT"),
    INTERNAL_SERVER_ERROR_EXCEPTION(50001, "INTERNAL_SERVER_ERROR"),
    UNAUTHORIZED_EXCEPTION(40101, "UNAUTHORIZED"),
    FORBIDDEN_EXCEPTION(40301, "FORBIDDEN");

    private final int code;
    private final String reason;

    ExceptionCodes(int code, String reason) {
        this.code = code;
        this.reason = reason;
    }

    @Override
    public String toString() {
        return code + " " +
                reason;
    }
}
