package com.epam.esm.service;

import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.UserDto;

public interface UserService extends CRDService<UserDto> {
    AuthResponseDto signUp(UserDto request);

    AuthResponseDto signIn(UserDto request);
}
