package com.epam.esm.repo.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.TagRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ContextConfiguration(classes = H2DataBaseTestConfig.class)
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
class TagDaoImplTest {
    private static final String INSERT_QUERY = "INSERT INTO tags(tag_name) values(?)";
    private static final String GET_BY_NAME_QUERY = "SELECT * FROM tags WHERE tag_name=?";
    @Autowired
    private TagRepo tagRepo;
    Tag tag1 = new Tag(1, "gym");
    Tag tag2 = new Tag(2, "cheap");
    Tag tag3 = new Tag(3, "rest");


    @Test
    public void testGetAll() throws DaoException {
        List<Tag> actual = tagRepo.findAll();
        List<Tag> expected = Arrays.asList(tag2, tag1, tag3);

        assertEquals(expected, actual);
    }
}