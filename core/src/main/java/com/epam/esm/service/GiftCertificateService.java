package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;

/**
 * Interface {@code GiftCertificateService} describes abstract behavior for working with {@link GiftCertificate} objects.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface GiftCertificateService extends CRUDService<GiftCertificate> {

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;
}
