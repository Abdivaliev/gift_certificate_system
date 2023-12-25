package com.epam.esm.service.validator;

import com.epam.esm.dto.OrderDto;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exception.ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID;
import static com.epam.esm.exception.ExceptionMessageKey.BAD_USER_ID;

@UtilityClass
public class OrderValidator {
    private final int MIN_ID = 1;


    public void validate(OrderDto orderDto, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(orderDto.getId(), er);
        validateUserId(orderDto.getUserId(), er);
        validateGiftCertificateId(orderDto.getGiftCertificateId(), er);
    }

    public void validateUserId(long userId, ExceptionResult er) {
        if (userId < MIN_ID) {
            er.addException(BAD_USER_ID, userId);
        }
    }

    public void validateGiftCertificateId(long giftCertificateId, ExceptionResult er) {
        if (giftCertificateId < MIN_ID) {
            er.addException(BAD_GIFT_CERTIFICATE_ID, giftCertificateId);
        }
    }
}
