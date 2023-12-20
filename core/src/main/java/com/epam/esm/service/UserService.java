package com.epam.esm.service;

import com.epam.esm.dto.SignInDto;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.SignUpDto;

public interface UserService extends CRDService<UserDto> {
    AuthResponseDto signUp(SignUpDto request);

    AuthResponseDto signIn(SignInDto request);
}
