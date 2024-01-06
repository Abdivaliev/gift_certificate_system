package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Set;

public interface GiftCertificateService extends CRUDService<GiftCertificateDto>{
    List<GiftCertificateDto> doFilter(MultiValueMap<String, String> allRequestParams, int page, int size);
    List<GiftCertificateDto> findByTagNames(Set<String> tagNames, int page, int size);
}
