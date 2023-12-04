package com.epam.esm.repo.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.TagRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {
    @Autowired
    private TagRepo tagRepo;
    Tag tag1 = new Tag(1, "gym");
    Tag tag2 = new Tag(2, "cheap");
    Tag tag3 = new Tag(3, "rest");


    @Test
    public void findAll() throws DaoException {
        List<Tag> actual = tagRepo.findAll();
        List<Tag> expected = Arrays.asList(tag2, tag1, tag3);

        assertEquals(expected, actual);
    }

    @Test
    void findById() throws DaoException {
        Tag actual = tagRepo.findById(tag3.getId());
        assertEquals(tag3, actual);
    }

    @Test
    void findByName() throws DaoException {
        List<Tag> actual = tagRepo.findByName(tag1.getName());
        List<Tag> expected = Collections.singletonList(tag1);

        assertEquals(expected, actual);
    }
}