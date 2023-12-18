package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dao.impl.UserDaoImpl;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.dto.converter.impl.UserConverter;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private CRDDao<User> userDao;

    @Mock
    private Converter<User, UserDto> converter;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    UserDto userDto = new UserDto();
    User user = new User();

    @Test
    void findById() {

    }

    @Test
    void findAll() {
    }
}