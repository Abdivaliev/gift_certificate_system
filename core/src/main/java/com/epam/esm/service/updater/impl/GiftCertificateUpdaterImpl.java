package com.epam.esm.service.updater.impl;

import com.epam.esm.service.updater.Updater;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;


@Component
public class GiftCertificateUpdaterImpl implements Updater<GiftCertificate> {


    @Override
    public GiftCertificate updateObject(GiftCertificate newGiftCertificate, GiftCertificate oldGiftCertificate) {
        Optional.ofNullable(newGiftCertificate.getName()).ifPresent(oldGiftCertificate::setName);
        Optional.ofNullable(newGiftCertificate.getDescription()).ifPresent(oldGiftCertificate::setDescription);
        Optional.ofNullable(newGiftCertificate.getPrice()).ifPresent(oldGiftCertificate::setPrice);
        Optional.ofNullable(newGiftCertificate.getDuration()).filter(duration -> duration != 0).ifPresent(oldGiftCertificate::setDuration);
        Optional.ofNullable(newGiftCertificate.getTags()).ifPresent(oldGiftCertificate::setTags);
        return oldGiftCertificate;
    }

    @Override
    public Set<GiftCertificate> updateListFromDatabase(Set<GiftCertificate> newListOfGiftCertificates) {
        throw new UnsupportedOperationException();
    }
}
