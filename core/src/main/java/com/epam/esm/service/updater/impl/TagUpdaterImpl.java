package com.epam.esm.service.updater.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.repo.TagRepo;
import com.epam.esm.service.updater.Updater;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class TagUpdaterImpl implements Updater<Tag> {
    private final TagRepo tagRepo;

    public TagUpdaterImpl(TagRepo tagRepo) {
        this.tagRepo = tagRepo;
    }

    @Override
    public Tag updateObject(Tag newTag, Tag oldTag) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Set<Tag> updateListFromDatabase(Set<Tag> newListOfTags) {
        if (newListOfTags == null) {
            return new HashSet<>();
        }
        return newListOfTags.stream()
                .map(tag -> tagRepo.findByName(tag.getName()).orElse(tag))
                .collect(Collectors.toSet());
    }

}
