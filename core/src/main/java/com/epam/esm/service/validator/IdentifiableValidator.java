package com.epam.esm.service.validator;

import com.epam.esm.exceptions.IncorrectParameterException;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_ID;


@UtilityClass
public class IdentifiableValidator {
    private final int MIN_ID = 1;

    public void validateId(long id) throws IncorrectParameterException {
        if (id < MIN_ID) {
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}
