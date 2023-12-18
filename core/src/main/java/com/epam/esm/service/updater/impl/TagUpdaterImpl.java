package com.epam.esm.service.updater.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.service.updater.Updater;
import com.epam.esm.entity.Tag;

import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class TagUpdaterImpl implements Updater<Tag> {
    private final TagDao tagDao;

    public TagUpdaterImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public Set<Tag> updateListFromDatabase(Set<Tag> newListOfTags) {
        if (newListOfTags == null) {
            return new HashSet<>();
        }
        return newListOfTags.stream()
                .map(tag -> tagDao.findByName(tag.getName()).orElse(tag))
                .collect(Collectors.toSet());
    }

    @Override
    public Tag updateObject(Tag newTag, Tag oldTag) {
        throw new UnsupportedOperationException();
    }

}
