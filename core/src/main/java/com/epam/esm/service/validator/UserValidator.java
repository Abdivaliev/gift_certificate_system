package com.epam.esm.service.validator;

import com.epam.esm.dto.UserDto;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exception.ExceptionMessageKey.*;


@UtilityClass
public class UserValidator {
    private final int MAX_LENGTH_NAME = 100;
    private final int MIN_LENGTH_PASSWORD = 4;


    public void validate(UserDto userDto, ExceptionResult er) {
//        IdentifiableValidator.validateExistenceOfId(userDto.getId(), er);
//        validateUsername(userDto.getUsername(), er);
//        validatePassword(userDto.getPassword(), er);
//        validateName(userDto.getName(), er);
    }


    public void validateUsername(String username, ExceptionResult er) {
//        if (username == null) {
//            er.addException(BAD_USERNAME, username);
//        }
    }


    public void validatePassword(String password, ExceptionResult er) {
//        if (password == null || password.length() < MIN_LENGTH_PASSWORD) {
//            er.addException(BAD_USER_PASSWORD, password);
//        }
    }

    private void validateName(String name, ExceptionResult er) {
        if (name == null || name.isEmpty() || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_USER_NAME, name);
        }
    }
}
