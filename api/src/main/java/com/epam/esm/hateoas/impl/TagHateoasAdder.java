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
    public TagDto addLinks(TagDto tagDto) {
            tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(tagDto.getId())).withSelfRel());
            tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).delete(tagDto.getId())).withRel("delete"));
            tagDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(tagDto)).withRel("save"));
        return tagDto;
    }
}
