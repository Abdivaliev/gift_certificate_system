package com.epam.esm.service.impl;

import com.epam.esm.config.H2DataBaseTestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.repo.impl.TagDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {

    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;

    Tag tag1 = new Tag(1, "gym");
    Tag tag2 = new Tag(2, "cheap");
    Tag tag3 = new Tag(3, "rest");

    @Test
    public void findById() throws DaoException, IncorrectParameterException {
        when(tagDao.findById(tag3.getId())).thenReturn(tag3);
        Tag actual = tagService.findById(tag3.getId());
        Tag expected = tag3;

        assertEquals(expected, actual);
    }

    @Test
    public void findAll() throws DaoException {
        List<Tag> tags = Arrays.asList(tag1, tag2, tag3);
        when(tagDao.findAll()).thenReturn(tags);
        List<Tag> actual = tagService.findAll();

        assertEquals(tags, actual);
    }

}