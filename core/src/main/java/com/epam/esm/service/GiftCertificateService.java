package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import org.springframework.util.MultiValueMap;

import java.util.List;


public interface GiftCertificateService extends CRUDService<GiftCertificate> {

    List<GiftCertificate> doFilter(MultiValueMap<String, String> requestParams) throws DaoException;
}
