package com.epam.esm.hateoas.impl;


import com.epam.esm.controller.GiftCertificateController;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class GiftCertificateHateoasAdder implements HateoasAdder<GiftCertificateDto> {
    private final HateoasAdder<TagDto> tagDtoHateoasAdder;
    private static final Class<GiftCertificateController> CONTROLLER = GiftCertificateController.class;

    public GiftCertificateHateoasAdder(HateoasAdder<TagDto> tagDtoHateoasAdder) {
        this.tagDtoHateoasAdder = tagDtoHateoasAdder;
    }


    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto) {
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).findById(giftCertificateDto.getId())).withRel("findById"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).delete(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).save(giftCertificateDto)).withRel("save"));
        giftCertificateDto.getTags().forEach(tagDtoHateoasAdder::addLinks);
    }

    @Override
    public void addLinks(GiftCertificateDto giftCertificateDto, int page, int size) {
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).findById(giftCertificateDto.getId())).withRel("findById"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).findAll(page, size)).withRel("findAll"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).update(giftCertificateDto.getId(), giftCertificateDto)).withRel("update"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).delete(giftCertificateDto.getId())).withRel("delete"));
        giftCertificateDto.add(linkTo(methodOn(CONTROLLER).save(giftCertificateDto)).withRel("save"));
        giftCertificateDto.getTags().forEach(tagDtoHateoasAdder::addLinks);
    }
}

