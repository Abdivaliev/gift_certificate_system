package com.epam.esm.service;

import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.UserDto;

import java.util.Map;

public interface UserService extends CRDService<UserDto> {
    Map<String, String> signUp(UserDto request);

    AuthResponseDto signIn(UserDto request);
}
