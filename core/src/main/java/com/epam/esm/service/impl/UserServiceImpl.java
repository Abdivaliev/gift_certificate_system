package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.User;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.CRDService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends AbstractService<User, UserDto> implements CRDService<UserDto> {

    public UserServiceImpl(CRDDao<User> dao, Converter<User, UserDto> converter) {
        super(dao, converter);
    }

    @Override
    @Transactional
    public UserDto save(UserDto dto) {
        throw new UnsupportedOperationException();
    }
}
