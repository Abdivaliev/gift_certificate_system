package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/v2/orders")
public class    OrderController {
    private final OrderService orderService;
    private final HateoasAdder<OrderDto> hateoasAdder;

    public OrderController(OrderService orderService, HateoasAdder<OrderDto> hateoasAdder) {
        this.orderService = orderService;
        this.hateoasAdder = hateoasAdder;
    }

    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public OrderDto findById(@PathVariable("id") long id) {
        OrderDto orderDto = orderService.findById(id);
        hateoasAdder.addLinks(orderDto);
        return orderDto;
    }

    @GetMapping(path = "/users/{userId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAllByUserId(@PathVariable long userId,
                                          @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                          @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<OrderDto> orderDtoList = orderService.findAllByUserId(userId, page, size);

        return orderDtoList.stream()
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    public List<OrderDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<OrderDto> orderDtoList = orderService.findAll(page, size);

        return orderDtoList.stream()
                .peek(hateoasAdder::addLinks)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto save(@RequestBody OrderDto orderDto) {
        OrderDto savedOrderDTo = orderService.save(orderDto);
        hateoasAdder.addLinks(savedOrderDTo);
        return orderDto;
    }
}
