package com.epam.esm.service.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.repo.impl.GiftCertificateDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceImplTest {

    @Mock
    private GiftCertificateDaoImpl giftCertificateDao = Mockito.mock(GiftCertificateDaoImpl.class);

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    Tag tag1 = new Tag(1, "gym");
    Tag tag2 = new Tag(2, "cheap");
    Tag tag3 = new Tag(3, "rest");

    GiftCertificate giftCertificate1 = new GiftCertificate(1L, "goldie's gym", "5 free visits", BigDecimal.valueOf(9.99), 7, "2020-08-29T06:12:15.156", "2020-08-29T06:12:15.156", List.of(tag1, tag2));
    GiftCertificate giftCertificate2 = new GiftCertificate(2L, "Kfc birthday", "50% off", BigDecimal.valueOf(5.55), 16, "2019-08-29T06:12:15.156", "2019-08-29T06:12:15.156", new ArrayList<>());
    GiftCertificate giftCertificate3 = new GiftCertificate(3L, "Silver screen", "one film", BigDecimal.valueOf(4.99), 9, "2018-08-29T06:12:15.156", "2018-08-29T06:12:15.156", List.of(tag2, tag3));
    String SORT_BY = "DESC";

    @Test
    void findById() throws DaoException, IncorrectParameterException {
        when(giftCertificateDao.findById(giftCertificate2.getId())).thenReturn(giftCertificate2);
        GiftCertificate actual = giftCertificateService.findById(giftCertificate2.getId());
        GiftCertificate expected = giftCertificate2;

        assertEquals(expected, actual);
    }

    @Test
    void findAll() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(giftCertificate1, giftCertificate2, giftCertificate3);
        when(giftCertificateDao.findAll()).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.findAll();

        assertEquals(giftCertificates, actual);
    }


    @Test
    void doFilter() throws DaoException {
        List<GiftCertificate> giftCertificates = Arrays.asList(giftCertificate2, giftCertificate1,giftCertificate3);
        MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("tag_name", tag2.getName());
        requestParams.add("sortByName", SORT_BY);

        Map<String, String> parameters = new HashMap<>();
        parameters.put("sortByName", SORT_BY);
        parameters.put("sortByCreateDate", null);
        parameters.put("partOfDescription", null);
        parameters.put("partOfName", null);
        parameters.put("tag_name", tag2.getName());
        parameters.put("name", null);
        when(giftCertificateDao.findWithFilters(parameters)).thenReturn(giftCertificates);
        List<GiftCertificate> actual = giftCertificateService.doFilter(requestParams);

        assertEquals(giftCertificates, actual);
    }
}