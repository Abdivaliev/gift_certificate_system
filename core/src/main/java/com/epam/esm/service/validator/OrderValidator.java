package com.epam.esm.service.validator;

import com.epam.esm.entity.Order;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import static com.epam.esm.exception.ExceptionMessageKey.BAD_GIFT_CERTIFICATE_ID;
import static com.epam.esm.exception.ExceptionMessageKey.BAD_USER_ID;

@UtilityClass
public class OrderValidator {
    private final int MIN_ID = 1;


    public void validate(Order order, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(order.getId(), er);
        validateUserId(order.getUser().getId(), er);
        validateGiftCertificateId(order.getGiftCertificate().getId(), er);
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
