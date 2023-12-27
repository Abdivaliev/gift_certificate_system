package com.epam.esm.controller;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.hateoas.HateoasAdder;
import com.epam.esm.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v3/orders")
public class OrderController {
    private final OrderService orderService;
    private final HateoasAdder<OrderDto> hateoasAdder;


    @GetMapping(path = "/{id}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public OrderDto findById(@PathVariable("id") long id) {
        OrderDto orderDto = orderService.findById(id);
        hateoasAdder.addLinks(orderDto);
        return orderDto;
    }

    @GetMapping(path = "/users/{userId}", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<OrderDto> findAllByUserId(@PathVariable long userId,
                                          @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                          @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<OrderDto> orderDtoList = orderService.findAllByUserId(userId, page, size);
        orderDtoList.forEach(orderDto -> hateoasAdder.addLinks(orderDto, page, size));
        return orderDtoList;
    }

    @GetMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('USER','ADMIN')")
    public List<OrderDto> findAll(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                  @RequestParam(value = "size", defaultValue = "5", required = false) int size) {

        List<OrderDto> orderDtoList = orderService.findAll(page, size);
        orderDtoList.forEach(orderDto -> hateoasAdder.addLinks(orderDto, page, size));
        return orderDtoList;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public OrderDto save(@RequestBody OrderDto orderDto) {
        OrderDto savedOrderDTo = orderService.save(orderDto);
        hateoasAdder.addLinks(savedOrderDTo);
        return savedOrderDTo;
    }
}
