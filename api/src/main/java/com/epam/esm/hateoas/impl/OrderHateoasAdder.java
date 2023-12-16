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
        if (orderDto!=null) {
            orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).findById(orderDto.getId())).withSelfRel());
            orderDto.add(linkTo(WebMvcLinkBuilder.methodOn(CONTROLLER).save(orderDto)).withRel("save"));
        }
    }
}
