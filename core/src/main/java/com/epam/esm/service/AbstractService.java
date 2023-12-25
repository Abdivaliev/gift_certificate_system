package com.epam.esm.service;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.validator.IdentifiableValidator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractService<E, D> implements CRDService<D> {
    protected final CRDDao<E> dao;
    protected final Converter<E, D> converter;

    public AbstractService(CRDDao<E> dao, Converter<E, D> converter) {
        this.dao = dao;
        this.converter = converter;
    }

    @Override
    @Transactional(readOnly = true)
    public D findById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<E> optionalEntity = dao.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        return converter.convertToDto(optionalEntity.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<D> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return dao.findAll(pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<E> optionalEntity = dao.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        dao.deleteById(id);
    }
}
