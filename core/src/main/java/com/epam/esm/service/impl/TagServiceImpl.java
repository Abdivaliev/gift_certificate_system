package com.epam.esm.service.impl;

import com.epam.esm.dto.MostUsedTagDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import com.epam.esm.exception.*;
import com.epam.esm.repo.TagRepo;
import com.epam.esm.repo.UserRepo;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.TagValidator;
import jakarta.persistence.Tuple;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {
    private final TagRepo tagRepo;
    private final UserRepo userRepo;
    private final Converter<Tag, TagDto> tagConvertor;
    private final Converter<Tuple, MostUsedTagDto> mostUsedTagDtoConverter;

    public TagServiceImpl(TagRepo tagRepo, UserRepo userRepo, Converter<Tag, TagDto> tagConvertor, Converter<Tuple, MostUsedTagDto> mostUsedTagDtoConverter) {
        this.tagRepo = tagRepo;
        this.userRepo = userRepo;
        this.tagConvertor = tagConvertor;
        this.mostUsedTagDtoConverter = mostUsedTagDtoConverter;
    }


    @Override
    public TagDto findById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<Tag> optionalEntity = tagRepo.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        return tagConvertor.convertToDto(optionalEntity.get());
    }

    @Override
    public List<TagDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return tagRepo.findAll(pageRequest).stream().map(tagConvertor::convertToDto).collect(Collectors.toList());
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
        boolean isTagExist = tagRepo.findByName(tagName).isPresent();
        if (isTagExist) {
            throw new ExistingEntityException(ExceptionMessageKey.TAG_EXIST);
        }


        Tag tag = tagConvertor.convertToEntity(tagDto);
        Tag savedTag = tagRepo.save(tag);
        return tagConvertor.convertToDto(savedTag);
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<Tag> foundEntity = tagRepo.findById(id);
        if (foundEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        tagRepo.deleteById(id);
    }


    @Override
    public List<MostUsedTagDto> findMostUsedTagByUserId(Long userId) {

        Optional<User> optionalEntity = userRepo.findById(userId);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        List<Tuple> tags = tagRepo.findMostUsedTagByUserId(userId);
        List<Tuple> explanation = tagRepo.findMostUsedTagByUserIdExplanation(userId);




        List<MostUsedTagDto> response = new ArrayList<>(tags.stream().map(mostUsedTagDtoConverter::convertToDto).toList());
        explanation.forEach(System.out::println);

        return response;
    }


}
