package com.epam.esm.service.impl;

import com.epam.esm.dto.SignInDto;
import com.epam.esm.dto.AuthResponseDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.SignUpDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Role;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repo.UserRepo;

import com.epam.esm.service.UserService;
import com.epam.esm.service.validator.IdentifiableValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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
    private final UserRepo userRepo;
    private final Converter<User, UserDto> converter;


    @Override
    public UserDto findById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<User> optionalEntity = userRepo.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        return converter.convertToDto(optionalEntity.get());
    }

    @Override
    public List<UserDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepo.findAll(pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    public AuthResponseDto signUp(SignUpDto request) {
        User user = new User();
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);
        userRepo.save(user);
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token);
    }

    @Override
    public AuthResponseDto signIn(SignInDto request) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        request.getUsername(), request.getPassword()
                ));

        User user = userRepo.findByUsername(request.getUsername()).orElseThrow();
        String token = jwtService.generateToken(user);
        return new AuthResponseDto(token);
    }

    @Override
    @Transactional
    public UserDto save(UserDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }
}
