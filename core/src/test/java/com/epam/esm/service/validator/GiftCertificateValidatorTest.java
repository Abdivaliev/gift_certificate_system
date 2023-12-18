package com.epam.esm.service.validator;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.exception.ExceptionResult;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GiftCertificateValidatorTest {
    GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
    Set<TagDto> tagDtoSet = new HashSet<>();
    TagDto tagDto = new TagDto();

    @Test
    void validateWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftCertificateValidator.validate(giftCertificateDto, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateForUpdateCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        GiftCertificateValidator.validateForUpdate(giftCertificateDto, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }


    @Test
    void validateCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        giftCertificateDto.setName("correctName");
        giftCertificateDto.setDescription("correctDescription");
        giftCertificateDto.setPrice(BigDecimal.TEN);
        giftCertificateDto.setDuration(20);
        tagDto.setName("correctTagName");
        tagDtoSet.add(tagDto);
        giftCertificateDto.setTags(tagDtoSet);
        GiftCertificateValidator.validate(giftCertificateDto, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateForUpdateWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        giftCertificateDto.setName("");
        giftCertificateDto.setDescription("correctDescription");
        giftCertificateDto.setPrice(BigDecimal.TEN);
        giftCertificateDto.setDuration(20);
        tagDto.setName("correctTagName");
        tagDtoSet.add(tagDto);
        giftCertificateDto.setTags(tagDtoSet);
        GiftCertificateValidator.validateForUpdate(giftCertificateDto, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }


    @Test
    void validateListOfTagsWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        tagDto.setId(1);
        tagDto.setName(" ");
        tagDtoSet.add(tagDto);
        GiftCertificateValidator.validateListOfTags(tagDtoSet, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateListOfTagsCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        tagDto.setName("tagName");
        tagDtoSet.add(tagDto);
        GiftCertificateValidator.validateListOfTags(tagDtoSet, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}