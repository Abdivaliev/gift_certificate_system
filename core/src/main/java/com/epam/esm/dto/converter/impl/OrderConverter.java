package com.epam.esm.dto.converter.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;


@Component
public class OrderConverter implements Converter<Order, OrderDto> {

    @Override
    public Order convertToEntity(OrderDto dto) {
        Order order = new Order();

        order.setPurchaseCost(dto.getPrice());
        order.setPurchaseTime(now());

        User user = new User();
        user.setId(dto.getUserId());
        order.setUser(user);

        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(dto.getGiftCertificateId());
        order.setGiftCertificate(giftCertificate);

        return order;
    }

    @Override
    public OrderDto convertToDto(Order entity) {
        OrderDto orderDto = new OrderDto();

        orderDto.setId(entity.getId());
        orderDto.setPrice(entity.getPurchaseCost());
        orderDto.setPurchaseTime(entity.getPurchaseTime());
        orderDto.setUserId(entity.getUser().getId());
        orderDto.setGiftCertificateId(entity.getGiftCertificate().getId());

        return orderDto;
    }
}
