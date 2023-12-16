package com.epam.esm.service.validator;

import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import java.util.Objects;

import static com.epam.esm.constant.FilterParameters.ASC;
import static com.epam.esm.constant.FilterParameters.DESC;
import static com.epam.esm.exception.ExceptionMessageKey.*;


@UtilityClass
public class IdentifiableValidator {
    private final int MIN_ID = 1;
    private final int EMPTY_ID = 0;


    public void validateId(long id, ExceptionResult er) {
        if (id < MIN_ID) {
            er.addException(BAD_ID, id);
        }
    }


    public void validateExistenceOfId(long id, ExceptionResult er) {
        if (id != EMPTY_ID) {
            er.addException(ID_EXISTENCE);
        }
    }


    public void validateSortType(String sortType, ExceptionResult er) {
        if (!(Objects.equals(ASC, sortType) ||
                Objects.equals(DESC, sortType))) {
            er.addException(BAD_SORT_TYPE, sortType);
        }
    }
}
