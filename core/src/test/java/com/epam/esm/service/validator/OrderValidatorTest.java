package com.epam.esm.service.validator;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ExceptionResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderValidatorTest {

    OrderDto orderDto=new OrderDto();

    @Test
    void validateWithIncorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        orderDto.setId(1);
        orderDto.setUserId(-1);
        orderDto.setGiftCertificateId(-5);
        OrderValidator.validate(orderDto, exceptionResult);
        assertFalse(exceptionResult.getExceptionMessages().isEmpty());
    }

    @Test
    void validateWithCorrectData() {
        ExceptionResult exceptionResult = new ExceptionResult();
        orderDto.setUserId(1);
        orderDto.setGiftCertificateId(5);
        OrderValidator.validate(orderDto, exceptionResult);
        assertTrue(exceptionResult.getExceptionMessages().isEmpty());
    }
}