package com.epam.esm.hateoas.impl;

import com.epam.esm.controller.OrderController;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;


@Component
public class OrderHateoasAdder implements HateoasAdder<OrderDto> {
    private static final Class<OrderController> CONTROLLER = OrderController.class;

    @Override
    public void addLinks(OrderDto orderDto) {
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(orderDto.getId())).withRel("findById"));
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(orderDto)).withRel("save"));
    }
    @Override
    public void addLinks(OrderDto orderDto,int page,int size) {
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(orderDto.getId())).withRel("findById"));
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findAll(page, size)).withRel("findAll"));
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findAllByUserId(orderDto.getUserId(), page, size)).withRel("findAllByUserId"));
        orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(orderDto)).withRel("save"));
    }
}
