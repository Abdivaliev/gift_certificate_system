package com.epam.esm.convertor.extractor.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.convertor.extractor.FieldExtractor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import static com.epam.esm.constants.ColumnNames.*;

/**
 * Implementation of the FieldExtractor interface for GiftCertificate.
 * This class is used to extract fields from a GiftCertificate object.
 */
@Component
public class GiftCertificateFieldExtractor implements FieldExtractor<GiftCertificate> {

    @Override
    public Map<String, String> extract(GiftCertificate giftCertificate) {
        String name = giftCertificate.getName();
        String description = giftCertificate.getDescription();
        String idString = String.valueOf(giftCertificate.getId());
        String updateDateString = giftCertificate.getUpdatedDate();
        String priceString = String.valueOf(giftCertificate.getPrice()).equals("null") ? null : String.valueOf(giftCertificate.getPrice());
        String durationString = String.valueOf(giftCertificate.getDuration()).equals("0") ? null : String.valueOf(giftCertificate.getDuration());

        Map<String, String> map = new HashMap<>();
        map.put(NAME, name);
        map.put(DESCRIPTION, description);
        map.put(PRICE, priceString);
        map.put(DURATION, durationString);
        map.put(UPDATE_DATE, updateDateString);
        map.put(ID, idString);
        return map;
    }
}
