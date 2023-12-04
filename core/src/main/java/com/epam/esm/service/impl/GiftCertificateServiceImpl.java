package com.epam.esm.service.impl;


import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.GiftCertificateRepo;
import com.epam.esm.repo.TagRepo;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.epam.esm.constants.FilterParameters.*;
import static java.time.LocalDateTime.now;
/**
 * Class {@code GiftCertificateServiceImpl} is implementation of interface {@link GiftCertificateService}
 * and intended to work with {@link GiftCertificate} objects.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Service
public class GiftCertificateServiceImpl extends AbstractService<GiftCertificate> implements GiftCertificateService {
    private final GiftCertificateRepo giftCertificateRepo;
    private final TagRepo tagRepo;

    @Autowired
    public GiftCertificateServiceImpl(TagRepo tagRepo, GiftCertificateRepo giftCertificateRepo) {
        super(giftCertificateRepo);
        this.tagRepo = tagRepo;
        this.giftCertificateRepo = giftCertificateRepo;

    }

    @Override
    public void save(GiftCertificate giftCertificate) throws DaoException {
        giftCertificate.setCreatedDate(now());
        giftCertificate.setUpdatedDate(now());
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagRepo.findAll();
        saveNewTags(requestTags, createdTags);
        dao.save(giftCertificate);
    }

    @Override
    public void update(long id, GiftCertificate giftCertificate) throws DaoException {
        giftCertificate.setId(id);
        giftCertificate.setUpdatedDate(now());
        List<Tag> requestTags = giftCertificate.getTags();
        List<Tag> createdTags = tagRepo.findAll();
        saveNewTags(requestTags, createdTags);
        giftCertificateRepo.update(giftCertificate);
    }

    @Override
    public List<GiftCertificate> doFilter(MultiValueMap<String, String> fields) throws DaoException {
        Map<String, String> map = new HashMap<>();
        map.put(NAME, getSingleParameter(fields, NAME));
        map.put(TAG_NAME, getSingleParameter(fields, TAG_NAME));
        map.put(PART_OF_NAME, getSingleParameter(fields, PART_OF_NAME));
        map.put(PART_OF_DESCRIPTION, getSingleParameter(fields, PART_OF_DESCRIPTION));
        map.put(SORT_BY_NAME, getSingleParameter(fields, SORT_BY_NAME));
        map.put(SORT_BY_CREATE_DATE, getSingleParameter(fields, SORT_BY_CREATE_DATE));

        return giftCertificateRepo.findWithFilters(map);
    }

    private void saveNewTags(List<Tag> requestTags, List<Tag> createdTags) throws DaoException {
        if (requestTags == null) {
            return;
        }
        Set<String> createdTagNames = createdTags.stream()
                .map(Tag::getName)
                .collect(Collectors.toSet());

        for (Tag requestTag : requestTags) {
            if (!createdTagNames.contains(requestTag.getName())) {
                tagRepo.save(requestTag);
            }
        }
    }

    private String getSingleParameter(MultiValueMap<String, String> fields, String parameter) {
        if (fields.containsKey(parameter)) {
            return fields.get(parameter).get(0);
        } else {
            return null;
        }
    }


}
