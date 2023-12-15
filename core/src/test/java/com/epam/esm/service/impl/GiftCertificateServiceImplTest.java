package com.epam.esm.service.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.dao.impl.GiftCertificateDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {
    private static final Tag TAG_1 = new Tag(1, "gym");
    private static final Tag TAG_2 = new Tag(2, "cheap");
    private static final Tag TAG_3 = new Tag(3, "rest");
    private static final Timestamp TIME = Timestamp.valueOf("2023-12-05 12:58:49.541960");

    private static final GiftCertificate GIFT_CERTIFICATE1 =
            new GiftCertificate(1L, "goldie's gym", "5 free visits", BigDecimal.valueOf(9.99),
                    7, TIME, TIME, List.of(TAG_1, TAG_2));
    private static final GiftCertificate GIFT_CERTIFICATE2 =
            new GiftCertificate(2L, "Kfc birthday", "50% off", BigDecimal.valueOf(5.55),
                    16, TIME, TIME, new ArrayList<>());
    private static final GiftCertificate GIFT_CERTIFICATE3 =
            new GiftCertificate(3L, "Silver screen", "one film", BigDecimal.valueOf(4.99),
                    9, TIME, TIME, List.of(TAG_2, TAG_3));
    String SORT_BY = "DESC";

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;


    @Test
    void findById() throws DaoException {
        when(giftCertificateDao.findById(GIFT_CERTIFICATE2.getId())).thenReturn(GIFT_CERTIFICATE2);
        GiftCertificate actual = giftCertificateService.findById(GIFT_CERTIFICATE2.getId());

        assertEquals(GIFT_CERTIFICATE2, actual);
    }

    @Test
    void findAll() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE1, GIFT_CERTIFICATE2, GIFT_CERTIFICATE3);
        when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.findAll();

        assertEquals(giftCertificates, actual);
    }


    @Test
    void doFilter() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(GIFT_CERTIFICATE2, GIFT_CERTIFICATE1, GIFT_CERTIFICATE3);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag_name", TAG_2.getName());
        requestParams.add("sortByName", SORT_BY);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("sortByName", SORT_BY);
        parameters.put("sortByCreateDate", null);
        parameters.put("partOfDescription", null);
        parameters.put("partOfName", null);
        parameters.put("tag_name", TAG_2.getName());
        parameters.put("name", null);
        when(giftCertificateDao.findWithFilters(parameters)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.doFilter(requestParams);

        assertEquals(giftCertificates, actual);
    }
}