package com.epam.esm.service.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.exception.*;
import com.epam.esm.service.updater.Updater;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.TagValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;


import java.util.*;
import java.util.stream.Collectors;

import static com.epam.esm.constant.ColumnNames.NAME;
import static com.epam.esm.constant.ColumnNames.TAG_NAME;
import static com.epam.esm.constant.FilterParameters.SORT_BY_CREATE_DATE;
import static com.epam.esm.constant.FilterParameters.SORT_BY_NAME;

@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate, GiftCertificateDto> implements GiftCertificateService {
    private final GiftCertificateDao giftCertificateDao;

    private final Updater<GiftCertificate> giftCertificateUpdater;
    private final Updater<Tag> tagUpdater;

    @Autowired
    public GiftCertificateServiceImpl(GiftCertificateDao giftCertificateDao, Converter<GiftCertificate, GiftCertificateDto> converter, Updater<GiftCertificate> giftCertificateUpdater, Updater<Tag> tagUpdater) {
        super(giftCertificateDao, converter);
        this.giftCertificateDao = giftCertificateDao;
        this.giftCertificateUpdater = giftCertificateUpdater;
        this.tagUpdater = tagUpdater;
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto giftCertificateDto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftCertificateValidator.validate(giftCertificateDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        String giftCertificateName = giftCertificateDto.getName();
        boolean isGiftCertificateExist = giftCertificateDao.findByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist) {
            throw new ExistingEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        GiftCertificate giftCertificate = converter.convertToEntity(giftCertificateDto);

        Set<Tag> tagsToPersist = tagUpdater.updateListFromDatabase(giftCertificate.getTags());
        giftCertificate.setTags(tagsToPersist);

        return converter.convertToDto(dao.save(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<GiftCertificate> foundGiftCertificate = giftCertificateDao.findById(id);
        if (foundGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        giftCertificateDao.deleteById(id);
    }

    @Override
    @Transactional
    public GiftCertificateDto update(long id, GiftCertificateDto giftCertificateDto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        GiftCertificateValidator.validateForUpdate(giftCertificateDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<GiftCertificate> oldGiftCertificate = dao.findById(id);
        if (oldGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        String giftCertificateName = giftCertificateDto.getName();
        boolean isGiftCertificateExist = giftCertificateDao.findByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist && !oldGiftCertificate.get().getName().equals(giftCertificateName)) {
            throw new ExistingEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        GiftCertificate giftCertificate = converter.convertToEntity(giftCertificateDto);

        giftCertificate.setTags(tagUpdater.updateListFromDatabase(giftCertificate.getTags()));
        GiftCertificate newGiftCertificate = giftCertificateUpdater.updateObject(giftCertificate, oldGiftCertificate.get());
        return converter.convertToDto(giftCertificateDao.update(newGiftCertificate));
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDto> doFilter(MultiValueMap<String, String> requestParams, int page, int size) {
        ExceptionResult exceptionResult = new ExceptionResult();
        String name = getSingleParameter(requestParams, NAME);
        if (name != null) {
            GiftCertificateValidator.validateName(name, exceptionResult);
        }
        List<String> tagNames = requestParams.get(TAG_NAME);
        if (tagNames != null) {
            for (String tagName : tagNames) {
                TagValidator.validateName(tagName, exceptionResult);
            }
        }
        String sortNameType = getSingleParameter(requestParams, SORT_BY_NAME);
        if (sortNameType != null) {
            IdentifiableValidator.validateSortType(sortNameType.toUpperCase(), exceptionResult);
        }
        String sortCreateDateType = getSingleParameter(requestParams, SORT_BY_CREATE_DATE);
        if (sortCreateDateType != null) {
            IdentifiableValidator.validateSortType(sortCreateDateType.toUpperCase(), exceptionResult);
        }
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        PageRequest pageRequest = PageRequest.of(page, size);
        return giftCertificateDao.findWithFilters(requestParams, pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDto> findByTagNames(Set<String> tagNames, int page, int size) {
        if (tagNames == null || tagNames.isEmpty()) {
            return findAll(page, size);
        } else {

            ExceptionResult exceptionResult = new ExceptionResult();

            for (String tagName : tagNames) {
                TagValidator.validateName(tagName, exceptionResult);
            }

            PageRequest pageRequest = PageRequest.of(page, size);

            return giftCertificateDao.findByTagNames(tagNames, pageRequest)
                    .stream()
                    .map(converter::convertToDto)
                    .toList();
        }
    }

    private String getSingleParameter(MultiValueMap<String, String> fields, String parameter) {
        return fields.getFirst(parameter);
    }
}
