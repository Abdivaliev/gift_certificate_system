package com.epam.esm.dao;

import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDao extends CRUDDao<GiftCertificate> {
    Optional<GiftCertificate> findByName(String name);
    List<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, PageRequest pageableDTo);
}
