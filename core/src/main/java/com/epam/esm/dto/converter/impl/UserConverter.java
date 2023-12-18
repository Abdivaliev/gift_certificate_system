package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<User, UserDto> {

    @Override
    public User convertToEntity(UserDto dto) {
        User user = new User();

        user.setName(dto.getName());

        return user;
    }

    @Override
    public UserDto convertToDto(User entity) {
        UserDto userDto = new UserDto();

        userDto.setId(entity.getId());
        userDto.setName(entity.getName());
        userDto.setCreatedDate(entity.getCreatedDate());
        userDto.setUpdatedDate(entity.getUpdatedDate());

        return userDto;
    }
}
