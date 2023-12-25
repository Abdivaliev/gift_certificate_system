package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.TagValidator;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static com.epam.esm.exception.ExceptionMessageKey.TAG_NOT_FOUND;

@Service
public class TagServiceImpl extends AbstractService<Tag, TagDto> implements TagService {
    private final TagDao tagDao;
    private final CRDDao<User> userDao;


    public TagServiceImpl(Converter<Tag, TagDto> converter, TagDao tagDao, CRDDao<User> userDao) {
        super(tagDao, converter);
        this.tagDao = tagDao;
        this.userDao = userDao;

    }


    @Override
    @Transactional
    public TagDto save(TagDto tagDto) {

        ExceptionResult exceptionResult = new ExceptionResult();
        TagValidator.validate(tagDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        String tagName = tagDto.getName();
        boolean isTagExist = tagDao.findByName(tagName).isPresent();
        if (isTagExist) {
            throw new ExistingEntityException(ExceptionMessageKey.TAG_EXIST);
        }


        Tag tag = converter.convertToEntity(tagDto);
        Tag savedTag = tagDao.save(tag);
        return converter.convertToDto(savedTag);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<Tag> foundEntity = tagDao.findById(id);
        if (foundEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        tagDao.deleteById(id);
    }

    @Override
    @Transactional
    public List<TagDto> findMostUsedTagListByUserId(Long userId, int page, int size) {

        Optional<User> optionalEntity = userDao.findById(userId);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        PageRequest pageRequest = PageRequest.of(page, size);
        return tagDao.findMostUsedTagListByUserId(userId, pageRequest).stream().map(converter::convertToDto).toList();
    }

}
