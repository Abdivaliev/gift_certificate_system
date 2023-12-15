package com.epam.esm.dao.impl;

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
import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class GiftCertificateDaoImplTest {
    private static final String SORT_BY = "DESC";
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


    @Autowired
    private GiftCertificateDaoImpl giftCertificateDao;


    @Test
    void testGetById() throws DaoException {
        GiftCertificate actual = giftCertificateDao.findById(GIFT_CERTIFICATE2.getId());
        assertEquals(GIFT_CERTIFICATE2, actual);
    }

    @Test
    void testGetAll() throws DaoException {
        List<GiftCertificate> actual = giftCertificateDao.findAll();

        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE1, GIFT_CERTIFICATE2, GIFT_CERTIFICATE3);
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
        List<GiftCertificate> expected = Arrays.asList(GIFT_CERTIFICATE1, GIFT_CERTIFICATE3);

        assertEquals(expected, actual);
    }
}