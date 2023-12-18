package com.epam.esm.service.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.dto.converter.impl.TagConverter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionResult;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TagValidatorTest {
    private static final String INCORRECT_NAME = "qw";
    private static final String CORRECT_NAME = "tagName";

    @Test
    void validateWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        TagDto incorrectTag = new TagDto();
        incorrectTag.setName(INCORRECT_NAME);
        TagValidator.validate(incorrectTag, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateWithCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        TagDto correctTag = new TagDto();
        correctTag.setName(CORRECT_NAME);
        TagValidator.validate(correctTag, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateNameWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        TagValidator.validateName(INCORRECT_NAME, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateNameWithCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        TagValidator.validateName(CORRECT_NAME, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}