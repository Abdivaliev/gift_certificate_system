package com.epam.esm.repo;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;

public interface GiftCertificateRepo extends CRUDDao<GiftCertificate> {
    List<GiftCertificate> findWithFilters(Map<String, String> fields) throws DaoException;
}
