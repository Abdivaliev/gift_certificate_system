package com.epam.esm.repo;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;

import java.util.List;
import java.util.Map;
/**
 * Interface {@code GiftCertificateDao} describes abstract behavior for working with gift_certificates table in database.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface GiftCertificateRepo extends CRUDDao<GiftCertificate> {
    List<GiftCertificate> findWithFilters(Map<String, String> fields) throws DaoException;
}
