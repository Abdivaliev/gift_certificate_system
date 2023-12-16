package com.epam.esm.service;

import com.epam.esm.dto.OrderDto;

import java.util.List;

public interface OrderService extends CRDService<OrderDto> {
    List<OrderDto> findAllByUserId(long userId,  int page,int size);
}
