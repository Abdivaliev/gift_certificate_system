package com.epam.esm.service.impl;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.repo.GiftCertificateRepo;
import com.epam.esm.repo.OrderRepo;
import com.epam.esm.repo.UserRepo;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.ExceptionMessageKey.USER_NOT_FOUND;


@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepo orderRepo;
    private final UserRepo userRepo;
    private final Converter<Order, OrderDto> orderConverter;
    private final GiftCertificateRepo giftCertificateRepo;

    @Autowired
    public OrderServiceImpl(OrderRepo orderRepo, UserRepo userRepo, Converter<Order, OrderDto> orderConverter, GiftCertificateRepo giftCertificateRepo) {
        this.orderRepo = orderRepo;
        this.userRepo = userRepo;
        this.orderConverter = orderConverter;
        this.giftCertificateRepo = giftCertificateRepo;
    }

    @Override
    public OrderDto findById(long id) {
        return null;
    }

    @Override
    public List<OrderDto> findAll(int page, int size) {
        return null;
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validate(orderDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<User> optionalUser = userRepo.findById(orderDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException(USER_NOT_FOUND);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateRepo.findById(orderDto.getGiftCertificateId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND);
        }

        Order order = orderConverter.convertToEntity(orderDto);

        order.setUser(optionalUser.get());
        order.setGiftCertificate(optionalGiftCertificate.get());

        order.setPurchaseCost(optionalGiftCertificate.get().getPrice());
        Order savedOrder = orderRepo.save(order);
        return orderConverter.convertToDto(savedOrder);
    }

    @Override
    public void deleteById(long id) {

    }

    @Override
    public List<OrderDto> findAllByUserId(long userId, int page, int size) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateUserId(userId, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        PageRequest pageRequest = PageRequest.of(page, size);

        return orderRepo.findAllByUserId(userId, pageRequest).stream().map(orderConverter::convertToDto).collect(Collectors.toList());
    }
}
