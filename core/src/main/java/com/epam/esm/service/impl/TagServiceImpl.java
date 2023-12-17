package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.ExceptionMessageKey.TAG_NOT_FOUND;

@Service
public class TagServiceImpl extends AbstractService<Tag, TagDto> implements TagService {
    private final TagDao tagDao;

    public TagServiceImpl(Converter<Tag, TagDto> converter, TagDao tagDao) {
        super(tagDao, converter);
        this.tagDao = tagDao;
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
            throw new DuplicateEntityException(ExceptionMessageKey.TAG_EXIST);
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
    public List<TagDto> findMostUsedTag() {
        List<Tag> tags = tagDao.findMostUsedTag();
        if (tags.isEmpty()) {
            throw new NoSuchEntityException(TAG_NOT_FOUND);
        }
        return  tags.stream().map(converter::convertToDto).collect(Collectors.toList());
    }
}
