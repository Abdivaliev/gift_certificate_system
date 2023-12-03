package com.epam.esm.exceptions;

import com.epam.esm.config.language.Translator;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import static com.epam.esm.exceptions.ExceptionCodes.METHOD_NOT_ALLOWED_EXCEPTION;
import static com.epam.esm.exceptions.ExceptionCodes.NOT_FOUND_EXCEPTION;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionsHandler {
    @ExceptionHandler(DaoException.class)
    public final ResponseEntity<Object> handleDaoExceptions(DaoException ex) {
        String details = Translator.toLocale(ex.getLocalizedMessage());
        com.epam.esm.exceptions.ErrorResponse errorResponse = new com.epam.esm.exceptions.ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<Object> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        String details = Translator.toLocale(ex.getLocalizedMessage());
        com.epam.esm.exceptions.ErrorResponse errorResponse = new com.epam.esm.exceptions.ErrorResponse(BAD_REQUEST.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = Translator.toLocale("exception.badRequest");
        com.epam.esm.exceptions.ErrorResponse errorResponse = new com.epam.esm.exceptions.ErrorResponse(BAD_REQUEST.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleBadRequestException() {
        String details = Translator.toLocale("exception.noHandler");
        com.epam.esm.exceptions.ErrorResponse errorResponse = new com.epam.esm.exceptions.ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> methodNotAllowedExceptionException() {
        String details = Translator.toLocale("exception.notSupported");
        com.epam.esm.exceptions.ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }
}
