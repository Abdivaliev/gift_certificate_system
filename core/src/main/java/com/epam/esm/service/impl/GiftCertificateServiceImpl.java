package com.epam.esm.service.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.*;
import com.epam.esm.repo.GiftCertificateRepo;
import com.epam.esm.repo.TagRepo;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.updater.Updater;
import com.epam.esm.service.validator.GiftCertificateValidator;
import com.epam.esm.service.validator.IdentifiableValidator;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.constant.ColumnNames.NAME;
import static com.epam.esm.constant.ColumnNames.TAG_NAME;
import static com.epam.esm.constant.FilterParameters.*;

@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final GiftCertificateRepo giftCertificateRepo;
    private final TagRepo tagRepo;
    private final Converter<GiftCertificate, GiftCertificateDto> converter;
    private final Updater<GiftCertificate> giftCertificateUpdater;
    private final Updater<Tag> tagUpdater;

    public GiftCertificateServiceImpl(GiftCertificateRepo giftCertificateRepo, TagRepo tagRepo, Converter<GiftCertificate, GiftCertificateDto> converter, Updater<GiftCertificate> giftCertificateUpdater, Updater<Tag> tagUpdater) {
        this.giftCertificateRepo = giftCertificateRepo;
        this.tagRepo = tagRepo;
        this.converter = converter;
        this.giftCertificateUpdater = giftCertificateUpdater;
        this.tagUpdater = tagUpdater;
    }

    @Override
    @Transactional(readOnly = true)
    public GiftCertificateDto findById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Optional<GiftCertificate> optionalEntity = giftCertificateRepo.findById(id);
        if (optionalEntity.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        return converter.convertToDto(optionalEntity.get());
    }

    @Override
    @Transactional(readOnly = true)
    public List<GiftCertificateDto> findAll(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return giftCertificateRepo.findAll(pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public GiftCertificateDto save(GiftCertificateDto dto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftCertificateValidator.validate(dto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        String giftCertificateName = dto.getName();
        boolean isGiftCertificateExist = giftCertificateRepo.findByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist) {
            throw new ExistingEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        GiftCertificate giftCertificate = converter.convertToEntity(dto);

        Set<Tag> tagsToPersist = tagUpdater.updateListFromDatabase(giftCertificate.getTags());
        giftCertificate.setTags(tagsToPersist);

        return converter.convertToDto(giftCertificateRepo.save(giftCertificate));
    }

    @Override
    @Transactional
    public void deleteById(long id) {
        ExceptionResult exceptionResult = new ExceptionResult();
        IdentifiableValidator.validateId(id, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<GiftCertificate> foundGiftCertificate = giftCertificateRepo.findById(id);
        if (foundGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }

        giftCertificateRepo.deleteById(id);
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

        Optional<GiftCertificate> oldGiftCertificate = giftCertificateRepo.findById(id);
        if (oldGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(ExceptionMessageKey.NO_ENTITY);
        }
        String giftCertificateName = giftCertificateDto.getName();
        boolean isGiftCertificateExist = giftCertificateRepo.findByName(giftCertificateName).isPresent();
        if (isGiftCertificateExist && !oldGiftCertificate.get().getName().equals(giftCertificateName)) {
            throw new ExistingEntityException(ExceptionMessageKey.GIFT_CERTIFICATE_EXIST);
        }

        GiftCertificate giftCertificate = converter.convertToEntity(giftCertificateDto);

        giftCertificate.setTags(tagUpdater.updateListFromDatabase(giftCertificate.getTags()));
        GiftCertificate newGiftCertificate = giftCertificateUpdater.updateObject(giftCertificate, oldGiftCertificate.get());
        return converter.convertToDto(giftCertificateRepo.save(newGiftCertificate));
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
        Set<Tag> tags=new HashSet<>();
        if (tagNames != null) {
            for (String tagName : tagNames) {
                TagValidator.validateName(tagName, exceptionResult);
                tagRepo.findByName(tagName).ifPresent(tags::add);
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

        String partOfName = getSingleParameter(requestParams, PART_OF_NAME);
        String partOfDescription = getSingleParameter(requestParams, PART_OF_DESCRIPTION);

//Sort sort=Sort.by(
//        Sort.Order.a
//)
        PageRequest pageRequest = PageRequest.of(page, size);
//        giftCertificateRepo.findAllByNameAndNameContainingAndDescriptionContainingAndTagsContains(name,partOfName,partOfDescription,tags,pageRequest);
        return null;
    }

    private String getSingleParameter(MultiValueMap<String, String> fields, String parameter) {
        return fields.getFirst(parameter);
    }
}
