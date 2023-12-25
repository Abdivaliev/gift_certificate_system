package com.epam.esm.hateoas.impl;


import com.epam.esm.controller.TagController;
import com.epam.esm.dto.TagDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class TagHateoasAdder implements HateoasAdder<TagDto> {
    private static final Class<TagController> CONTROLLER = TagController.class;
    @Override
    public void addLinks(TagDto tagDto) {
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(tagDto.getId())).withRel("findById"));
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).delete(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(tagDto)).withRel("save"));
    }
    @Override
    public void addLinks(TagDto tagDto,int page,int size) {
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(tagDto.getId())).withRel("findById"));
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).delete(tagDto.getId())).withRel("delete"));
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(tagDto)).withRel("save"));
        tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findAll(page, size)).withRel("findAll"));
    }
}
