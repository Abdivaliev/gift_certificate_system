package com.epam.esm.exceptions;

import lombok.*;
/**
 * Class representing an error response.
 * This class is used to send a structured error message to the client.
 */
@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
}
