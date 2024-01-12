package com.epam.esm.exception;


import com.epam.esm.dto.SecurityErrorResponse;
import com.epam.esm.config.language.Translator;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.Iterator;
import java.util.Map;

import static com.epam.esm.exception.ExceptionCodes.*;
import static org.springframework.http.HttpStatus.*;


@RestControllerAdvice
public class ExceptionsHandler {

    @ExceptionHandler(ExistingEntityException.class)
    public final ResponseEntity<Object> handleDuplicateEntityExceptions(ExistingEntityException ex) {
        String details = Translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(CONFLICT_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, CONFLICT);
    }

    @ExceptionHandler(NoSuchEntityException.class)
    public final ResponseEntity<Object> handleNoSuchEntityExceptions(NoSuchEntityException ex) {
        String details = Translator.toLocale(ex.getLocalizedMessage());
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(IncorrectParameterException.class)
    public final ResponseEntity<ErrorResponse> handleIncorrectParameterExceptions(IncorrectParameterException ex) {
        Iterator<Map.Entry<String, Object[]>> exceptions = ex.getExceptionResult().getExceptionMessages().entrySet().iterator();
        StringBuilder details = new StringBuilder();
        while (exceptions.hasNext()) {
            Map.Entry<String, Object[]> exception = exceptions.next();
            String message = Translator.toLocale(exception.getKey());
            String detail = String.format(message, exception.getValue());
            details.append(detail).append(' ');
        }
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details.toString());
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public final ResponseEntity<Object> handleUnsupportedOperationExceptions() {
        String details = Translator.toLocale("exception.unsupportedOperation");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class, JsonProcessingException.class})
    public final ResponseEntity<Object> handleBadRequestExceptions() {
        String details = Translator.toLocale("exception.badRequest");
        ErrorResponse errorResponse = new ErrorResponse(BAD_REQUEST_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public final ResponseEntity<Object> handleNoHandlerFoundException() {
        String details = Translator.toLocale("exception.noHandler");
        ErrorResponse errorResponse = new ErrorResponse(NOT_FOUND_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, NOT_FOUND);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public final ResponseEntity<Object> handleHttpRequestMethodNotSupportedException() {
        String details = Translator.toLocale("exception.notSupported");
        ErrorResponse errorResponse = new ErrorResponse(METHOD_NOT_ALLOWED_EXCEPTION.toString(), details);
        return new ResponseEntity<>(errorResponse, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public final ResponseEntity<Object> handleBadCredentialsException() {
        String details = Translator.toLocale("exception.invalidAuthorization");
        SecurityErrorResponse securityErrorResponse =
                new SecurityErrorResponse(UNAUTHORIZED_EXCEPTION.getCode(), UNAUTHORIZED_EXCEPTION.getReason(), details);
        return new ResponseEntity<>(securityErrorResponse, UNAUTHORIZED);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Object> handleAccessDeniedException() {
        String details = Translator.toLocale("exception.noRights");
        SecurityErrorResponse securityErrorResponse =
                new SecurityErrorResponse(FORBIDDEN_EXCEPTION.getCode(), FORBIDDEN_EXCEPTION.getReason(), details);
        return new ResponseEntity<>(securityErrorResponse, FORBIDDEN);
    }

    @ExceptionHandler(SignatureException.class)
    public final ResponseEntity<Object> handleSignatureException() {
        String details = Translator.toLocale("exception.badInput");
        SecurityErrorResponse securityErrorResponse =
                new SecurityErrorResponse(FORBIDDEN_EXCEPTION.getCode(), FORBIDDEN_EXCEPTION.getReason(), details);
        return new ResponseEntity<>(securityErrorResponse, BAD_REQUEST);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public final ResponseEntity<Object> handleExpiredJwtException() {
        String details = Translator.toLocale("exception.expiredToken");
        SecurityErrorResponse securityErrorResponse =
                new SecurityErrorResponse(FORBIDDEN_EXCEPTION.getCode(), FORBIDDEN_EXCEPTION.getReason(), details);
        return new ResponseEntity<>(securityErrorResponse, FORBIDDEN);
    }
}
