package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static java.time.LocalDateTime.now;


@Component
public class GiftCertificateConverter implements Converter<GiftCertificate, GiftCertificateDto> {
    private final Converter<Tag, TagDto> converter;

    public GiftCertificateConverter(Converter<Tag, TagDto> converter) {
        this.converter = converter;
    }

    @Override
    public GiftCertificate convertToEntity(GiftCertificateDto dto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setCreatedDate(now());
        giftCertificate.setUpdatedDate(now());
        giftCertificate.setName(dto.getName());
        giftCertificate.setDescription(dto.getDescription());
        giftCertificate.setPrice(dto.getPrice());
        giftCertificate.setDuration(dto.getDuration());
        giftCertificate.setTags(
                dto.getTagDtos().stream().map(converter::convertToEntity).collect(Collectors.toSet())
        );

        return giftCertificate;
    }

    @Override
    public GiftCertificateDto convertToDto(GiftCertificate entity) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();

        giftCertificateDto.setId(entity.getId());
        giftCertificateDto.setName(entity.getName());
        giftCertificateDto.setDescription(entity.getDescription());
        giftCertificateDto.setPrice(entity.getPrice());
        giftCertificateDto.setDuration(entity.getDuration());
        giftCertificateDto.setCreateDate(entity.getCreatedDate());
        giftCertificateDto.setUpdatedDate(entity.getUpdatedDate());
        giftCertificateDto.setTagDtos(
                entity.getTags().stream().map(converter::convertToDto).collect(Collectors.toSet())
        );

        return giftCertificateDto;
    }
}
