package com.epam.esm.service.validator;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ExceptionResult;
import lombok.experimental.UtilityClass;

import java.math.BigDecimal;
import java.util.Set;

import static com.epam.esm.exception.ExceptionMessageKey.*;


@UtilityClass
public class GiftCertificateValidator {
    private final int MAX_LENGTH_NAME = 45;
    private final int MIN_LENGTH_NAME = 3;
    private final int MAX_LENGTH_DESCRIPTION = 300;
    private final int MAX_SCALE = 2;
    private final BigDecimal MIN_PRICE = new BigDecimal("0.01");
    private final BigDecimal MAX_PRICE = new BigDecimal("999999.99");
    private final int MAX_DURATION = 366;
    private final int MIN_DURATION = 1;


    public void validate(GiftCertificate giftCertificate, ExceptionResult er) {
        IdentifiableValidator.validateExistenceOfId(giftCertificate.getId(), er);
        validateName(giftCertificate.getName(), er);
        validateDescription(giftCertificate.getDescription(), er);
        validatePrice(giftCertificate.getPrice(), er);
        validateDuration(giftCertificate.getDuration(), er);
        validateListOfTags(giftCertificate.getTags(), er);
    }

    public void validateForUpdate(GiftCertificate giftCertificate, ExceptionResult er) {
        if (giftCertificate.getName() != null) {
            validateName(giftCertificate.getName(), er);
        }
        if (giftCertificate.getDescription() != null) {
            validateDescription(giftCertificate.getDescription(), er);
        }
        if (giftCertificate.getPrice() != null) {
            validatePrice(giftCertificate.getPrice(), er);
        }
        if (giftCertificate.getDuration() != 0) {
            validateDuration(giftCertificate.getDuration(), er);
        }
        validateListOfTags(giftCertificate.getTags(), er);
    }


    public void validateListOfTags(Set<Tag> tags, ExceptionResult er) {
        if (tags == null) return;
        for (Tag tag : tags) {
            TagValidator.validate(tag, er);
        }
    }

    public void validateName(String name, ExceptionResult er) {
        if (name == null || name.length() < MIN_LENGTH_NAME || name.length() > MAX_LENGTH_NAME) {
            er.addException(BAD_GIFT_CERTIFICATE_NAME, name);
        }
    }

    private void validateDescription(String description, ExceptionResult er) {
        if (description == null || description.length() > MAX_LENGTH_DESCRIPTION) {
            er.addException(BAD_GIFT_CERTIFICATE_DESCRIPTION, description);
        }
    }

    private void validatePrice(BigDecimal price, ExceptionResult er) {
        if (price == null || price.scale() > MAX_SCALE
                || price.compareTo(MIN_PRICE) < 0 || price.compareTo(MAX_PRICE) > 0) {
            er.addException(BAD_GIFT_CERTIFICATE_PRICE, price);
        }
    }

    private void validateDuration(int duration, ExceptionResult er) {
        if (duration < MIN_DURATION || duration > MAX_DURATION) {
            er.addException(BAD_GIFT_CERTIFICATE_DURATION, duration);
        }
    }
}
