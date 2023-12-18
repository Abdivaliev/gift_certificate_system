package com.epam.esm.service.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.UserDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import com.epam.esm.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {
    @Mock
    private CRDDao<Tag> tagDao;

    @Mock
    private Converter<Tag, TagDto> converter;

    @InjectMocks
    private TagServiceImpl tagService;

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
    void findMostUsedTag() {
    }
}