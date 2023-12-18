package com.epam.esm.service.validator;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exception.ExceptionMessageKey.BAD_TAG_NAME;


@UtilityClass
public class TagValidator {
    private final int MAX_LENGTH_NAME = 20;
    private final int MIN_LENGTH_NAME = 3;


    public void validate(TagDto tagDto, ExceptionResult er) {
//        IdentifiableValidator.validateExistenceOfId(tagDto.getId(), er);
//        validateName(tagDto.getName(), er);
    }

    public void validateName(String name, ExceptionResult er) {
//        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
//            er.addException(BAD_TAG_NAME, name);
//        }
    }
}
