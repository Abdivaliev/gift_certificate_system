package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.updater.Updater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class GiftCertificateServiceImplTest {
    @Mock
    private CRDDao<GiftCertificate> giftCertificateDao;
    @Mock
    private Converter<GiftCertificate, GiftCertificateDto> converter;
    @Mock
    private Updater<GiftCertificate> giftCertificateUpdater;
    private Updater<Tag> tagUpdater;


    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save() {
    }

    @Test
    void deleteById() {
    }

    @Test
    void update() {
    }

    @Test
    void doFilter() {
    }
}