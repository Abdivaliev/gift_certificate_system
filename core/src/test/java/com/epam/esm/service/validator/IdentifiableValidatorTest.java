package com.epam.esm.service.validator;

import com.epam.esm.exception.ExceptionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IdentifiableValidatorTest {
    private static final String INCORRECT_SORT_TYPE = "sort";
    private static final String CORRECT_SORT_TYPE = "ASC";
    private static final long INCORRECT_ID = -15;
    private static final long CORRECT_ID = 15;
    @Test
    void ValidateIdWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(INCORRECT_ID, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void ValidateIdWithCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(CORRECT_ID, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void ValidateSortTypeWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateSortType(INCORRECT_SORT_TYPE, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void ValidateSortTypeWithCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateSortType(CORRECT_SORT_TYPE, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}