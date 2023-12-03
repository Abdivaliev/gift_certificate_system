package com.epam.esm.exceptions;

import lombok.*;
/**
 * Class representing an error response.
 * This class is used to send a structured error message to the client.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Getter
@AllArgsConstructor
@ToString
public class ErrorResponse {
    private int errorCode;
    private String errorMessage;
}
