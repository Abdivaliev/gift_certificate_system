package com.epam.esm.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;


@Getter
public class ExceptionResult {
    private final Map<String, Object[]> exceptionMessages;

    public ExceptionResult() {
        exceptionMessages = new HashMap<>();
    }

    public void addException(String messageCode, Object... arguments) {
        exceptionMessages.put(messageCode, arguments);
    }

}
