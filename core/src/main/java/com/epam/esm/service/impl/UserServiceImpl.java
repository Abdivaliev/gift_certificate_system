package com.epam.esm.service.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.*;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.service.UserService;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final PasswordEncoder passwordEncoder;
    private final JWTServiceImpl jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserDao userDao;
    private final Converter<User, UserDto> converter;


    @Override
    public UserDto findById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<User> optionalEntity = userDao.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        return converter.convertToDto(optionalEntity.get());
    }

    @Override
    public List<UserDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userDao.findAll(pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthResponseDto signUp(UserDto userDto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        UserValidator.validate(userDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        String username = userDto.getUsername();
        boolean isUserExist = userDao.findByUsername(username).isPresent();
        if (isUserExist) {
            throw new ExistingEntityException(ExceptionMessageKey.USER_EXIST);
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.USER);
        userDao.save(user);
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponseDto(accessToken, refreshToken);
    }

    @Override
    public AuthResponseDto signIn(UserDto userDto) {
        ExceptionResult exceptionResult = new ExceptionResult();

        UserValidator.validateUsername(userDto.getUsername(), exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        UserValidator.validatePassword(userDto.getPassword(), exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        userDto.getUsername(), userDto.getPassword()));

        User user = userDao.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new NoSuchEntityException(ExceptionMessageKey.USER_NOT_FOUND));

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);
        return new AuthResponseDto(accessToken, refreshToken);
    }

    @Override
    @Transactional
    public UserDto save(UserDto userDto) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }
}
