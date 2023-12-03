package com.epam.esm.service.validator;

import com.epam.esm.exceptions.IncorrectParameterException;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exceptions.ExceptionIncorrectParameterMessageCodes.BAD_ID;

/**
 * Class {@code IdentifiableValidator} provides methods to validate fields of {@link com.epam.esm.entity.Identifiable}.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@UtilityClass
public class IdentifiableValidator {
    private final int MIN_ID = 1;

    public void validateId(long id) throws IncorrectParameterException {
        if (id < MIN_ID) {
            throw new IncorrectParameterException(BAD_ID);
        }
    }
}
