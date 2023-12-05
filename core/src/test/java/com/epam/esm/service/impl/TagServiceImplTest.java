package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.impl.TagDaoImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TagServiceImplTest {
    private static final Tag TAG_1 = new Tag(1, "gym");
    private static final Tag TAG_2 = new Tag(2, "cheap");
    private static final Tag TAG_3 = new Tag(3, "rest");
    @Mock
    private TagDaoImpl tagDao = Mockito.mock(TagDaoImpl.class);

    @InjectMocks
    private TagServiceImpl tagService;


    @Test
    public void findById() throws DaoException {
        when(tagDao.findById(TAG_3.getId())).thenReturn(TAG_3);
        Tag actual = tagService.findById(TAG_3.getId());

        assertEquals(TAG_3, actual);
    }

    @Test
    public void findAll() throws DaoException {
        List<Tag> tags = Arrays.asList(TAG_1, TAG_2, TAG_3);
        when(tagDao.findAll()).thenReturn(tags);
        List<Tag> actual = tagService.findAll();

        assertEquals(tags, actual);
    }

}