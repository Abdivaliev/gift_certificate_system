package com.epam.esm.hateoas.impl;


import com.epam.esm.controller.UserController;
import com.epam.esm.dto.UserDto;

import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class UserHateoasAdder implements HateoasAdder<UserDto> {
    private static final Class<UserController> CONTROLLER = UserController.class;

    @Override
    public void addLinks(UserDto userDto) {
        userDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(userDto.getId())).withRel("findById"));
    }
    @Override
    public void addLinks(UserDto userDto,int page,int size) {
        userDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(userDto.getId())).withRel("findById"));
        userDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findAll(page,size)).withRel("findAll"));
    }
}
