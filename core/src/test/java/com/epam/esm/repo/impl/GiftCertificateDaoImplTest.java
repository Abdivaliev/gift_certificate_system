package com.epam.esm.repo.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoImplTest {

    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;

    Tag tag1 = new Tag(1, "gym");
    Tag tag2 = new Tag(2, "cheap");
    Tag tag3 = new Tag(3, "rest");

    GiftCertificate giftCertificate1 = new GiftCertificate(1L,"goldie's gym", "5 free visits", BigDecimal.valueOf(9.99), 7, "2020-08-29T06:12:15.156", "2020-08-29T06:12:15.156", List.of(tag1,tag2));
    GiftCertificate giftCertificate2 = new GiftCertificate(2L, "Kfc birthday", "50% off", BigDecimal.valueOf(5.55), 16, "2019-08-29T06:12:15.156", "2019-08-29T06:12:15.156",new ArrayList<>());
    GiftCertificate giftCertificate3 = new GiftCertificate(3L, "Silver screen", "one film", BigDecimal.valueOf(4.99), 9, "2018-08-29T06:12:15.156", "2018-08-29T06:12:15.156", List.of(tag2,tag3));

    String SORT_BY = "DESC";

    @Test
    void testGetById() throws DaoException {
        GiftCertificate actual = giftCertificateDao.findById(giftCertificate2.getId());
        GiftCertificate expected = giftCertificate2;

        assertEquals(expected, actual);
    }

    @Test
    void testGetAll() throws DaoException {
        List<GiftCertificate> actual = giftCertificateDao.findAll();

        List<GiftCertificate> expected = Arrays.asList(giftCertificate1, giftCertificate2, giftCertificate3);
        assertEquals(expected, actual);
    }


    @Test
    public void testFindWithFilters() throws DaoException {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("sortByName", SORT_BY);
        parameters.put("sortByCreateDate", null);
        parameters.put("partOfName", "e");
        parameters.put("partOfDescription", null);
        parameters.put("tag_name", null);
        parameters.put("name", null);
        List<GiftCertificate> actual = giftCertificateDao.findWithFilters(parameters);
        List<GiftCertificate> expected = Arrays.asList(giftCertificate1, giftCertificate3);

        assertEquals(expected, actual);
    }
}