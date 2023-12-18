package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.OrderDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class OrderServiceImplTest {
    @Mock
    private OrderDao orderDao;
    @Mock
    private CRDDao<User> userDao;
    @Mock
    private GiftCertificateDao giftCertificateDao;

    @Mock
    private Converter<Order, OrderDto> converter;

    @InjectMocks
    private OrderServiceImpl orderService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
    }

    @Test
    void findAllByUserId() {
    }
}