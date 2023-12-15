package com.epam.esm.dao.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.dao.TagDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {
    private static final Tag TAG_1 = new Tag(1, "gym");
    private static final Tag TAG_2 = new Tag(2, "cheap");
    private static final Tag TAG_3 = new Tag(3, "rest");
    @Autowired
    private TagDao tagDao;


    @Test
    public void findAll() throws DaoException {
        List<Tag> actual = tagDao.findAll();
        List<Tag> expected = Arrays.asList(TAG_2, TAG_1, TAG_3);

        assertEquals(expected, actual);
    }

    @Test
    void findById() throws DaoException {
        Tag actual = tagDao.findById(TAG_3.getId());
        assertEquals(TAG_3, actual);
    }

    @Test
    void findByName() throws DaoException {
        Tag actual = tagDao.findByName(TAG_1.getName());

        assertEquals(TAG_1, actual);
    }
}