package com.epam.esm.service.impl;

import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repo.UserRepo;
import com.epam.esm.service.CRDService;

import com.epam.esm.service.validator.IdentifiableValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements CRDService<UserDto> {

    private final UserRepo userRepo;
    private final Converter<User, UserDto> converter;

    public UserServiceImpl(UserRepo userRepo, Converter<User, UserDto> converter) {
        this.userRepo = userRepo;
        this.converter = converter;
    }

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
    @Transactional
    public UserDto save(UserDto dto) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }
}
