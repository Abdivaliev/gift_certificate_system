package com.epam.esm.dto;

public record SecurityErrorResponse(int statusCode, String title, String details) {
}
