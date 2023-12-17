package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.Pageable;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Order;
import com.epam.esm.entity.User;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import com.epam.esm.exception.NoSuchEntityException;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.OrderService;
import com.epam.esm.service.validator.OrderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.epam.esm.exception.ExceptionMessageKey.GIFT_CERTIFICATE_NOT_FOUND;
import static com.epam.esm.exception.ExceptionMessageKey.USER_NOT_FOUND;


@Service
public class OrderServiceImpl extends AbstractService<Order,OrderDto> implements OrderService {

    private final OrderDao orderDao;
    private final CRDDao<User> userDao;
    private final GiftCertificateDao giftCertificateDao;

    @Autowired
    public OrderServiceImpl(CRDDao<Order> dao, Converter<Order, OrderDto> converter, OrderDao orderDao, CRDDao<User> userDao, GiftCertificateDao giftCertificateDao) {
        super(dao, converter);
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.giftCertificateDao = giftCertificateDao;
    }

    @Override
    @Transactional
    public OrderDto save(OrderDto orderDto) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validate(orderDto, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }

        Optional<User> optionalUser = userDao.findById(orderDto.getUserId());
        if (optionalUser.isEmpty()) {
            throw new NoSuchEntityException(USER_NOT_FOUND);
        }

        Optional<GiftCertificate> optionalGiftCertificate = giftCertificateDao.findById(orderDto.getGiftCertificateId());
        if (optionalGiftCertificate.isEmpty()) {
            throw new NoSuchEntityException(GIFT_CERTIFICATE_NOT_FOUND);
        }

        Order order = converter.convertToEntity(orderDto);

        order.setUser(optionalUser.get());
        order.setGiftCertificate(optionalGiftCertificate.get());

        order.setPurchaseCost(optionalGiftCertificate.get().getPrice());
        return converter.convertToDto(dao.save(order));
    }

    @Override
    public List<OrderDto> findAllByUserId(long userId, int page, int size) {
        ExceptionResult exceptionResult = new ExceptionResult();
        OrderValidator.validateUserId(userId, exceptionResult);
        if (!exceptionResult.getExceptionMessages().isEmpty()) {
            throw new IncorrectParameterException(exceptionResult);
        }
        Pageable pageRequest = createPageRequest(page, size);

         return orderDao.findAllByUserId(userId, pageRequest).stream().map(converter::convertToDto).collect(Collectors.toList());
    }
}
