package com.epam.esm.service.updater.impl;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import static java.time.LocalDateTime.now;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class GiftCertificateUpdaterImplTest {
    private GiftCertificateUpdaterImpl updater;

    @BeforeEach
    public void setUp() {
        updater = Mockito.spy(new GiftCertificateUpdaterImpl());
    }


    @Test
    void updateObject() {
        LocalDateTime now = now();

        GiftCertificate oldGiftCertificate = getGiftCertificate(new GiftCertificate(), 1L, "giftCertificate",
                "description", 10.115, 1, now, now, 1L, "tag1", null, null);
        GiftCertificate newGiftCertificate = getGiftCertificate(new GiftCertificate(), 0L, "newGiftCertificate",
                "newGiftCertificate", 10.115, 2, null, null, 1L, "tag1", 2L, "tag2");
        GiftCertificate updatedGiftCertificate = getGiftCertificate(new GiftCertificate(), 1L, "newGiftCertificate",
                "newGiftCertificate", 10.115, 2, now, now, 1L, "tag1", 2L, "tag2");

        GiftCertificate actual = updater.updateObject(newGiftCertificate, oldGiftCertificate);

        assertEquals(updatedGiftCertificate, actual);
    }

    private GiftCertificate getGiftCertificate(GiftCertificate giftCertificate, Long id, String name, String desc, Double price, int duration, LocalDateTime createdDate, LocalDateTime updatedDate, Long tagId1, String tagName1, Long tagId2, String tagName2) {
        giftCertificate.setId(id);
        giftCertificate.setName(name);
        giftCertificate.setDescription(desc);
        giftCertificate.setDuration(duration);
        giftCertificate.setPrice(BigDecimal.valueOf(price));
        giftCertificate.setCreatedDate(createdDate);
        giftCertificate.setUpdatedDate(updatedDate);
        Tag tag1 = getTag(new Tag(), tagId1, tagName1);
        if (tagId2 != null) {
            Tag tag2 = getTag(new Tag(), tagId2, tagName2);
            giftCertificate.setTags(Set.of(tag1, tag2));
        }
        giftCertificate.setTags(Set.of(tag1));
        return giftCertificate;
    }

    private Tag getTag(Tag tag, Long id, String name) {
        tag.setId(id);
        tag.setName(name);
        return tag;
    }
}