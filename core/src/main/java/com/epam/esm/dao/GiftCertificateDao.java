package com.epam.esm.dao;

import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);
    List<GiftCertificate> findByTagNames(Set<String> tagNames, PageRequest pageRequest);
    List<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, PageRequest pageRequest);
}
