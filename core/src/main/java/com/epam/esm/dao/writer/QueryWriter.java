package com.epam.esm.dao.writer;

import com.epam.esm.entity.GiftCertificate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.constant.ColumnNames.*;
import static com.epam.esm.constant.FilterParameters.*;


@Component
public class QueryWriter{
    private static final Class<GiftCertificate> GIFT_CERTIFICATE_CLASS = GiftCertificate.class;

    public CriteriaQuery<GiftCertificate> writeGetQueryWithParam(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder) {
        CriteriaQuery<GiftCertificate> criteriaQuery = criteriaBuilder.createQuery(GIFT_CERTIFICATE_CLASS);
        Root<GiftCertificate> giftCertificateRoot = criteriaQuery.from(GIFT_CERTIFICATE_CLASS);
        List<Predicate> restrictions = new ArrayList<>();

        restrictions.addAll(addName(fields, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addTagNames(fields, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addPartOfName(fields, criteriaBuilder, giftCertificateRoot));
        restrictions.addAll(addPartOfDescription(fields, criteriaBuilder, giftCertificateRoot));
        criteriaQuery.select(giftCertificateRoot).where(restrictions.toArray(new Predicate[]{}));
        addSortByName(fields, criteriaBuilder, criteriaQuery, giftCertificateRoot);
        addSortByCreateDate(fields, criteriaBuilder, criteriaQuery, giftCertificateRoot);

        return criteriaQuery;
    }

    private List<Predicate> addTagNames(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                        Root<GiftCertificate> giftCertificateRoot) {
        return Optional.ofNullable(fields.get(TAG_NAME))
                .map(tagNames -> tagNames.stream()
                        .map(tagName -> criteriaBuilder.equal(giftCertificateRoot.join(TAGS).get(NAME), tagName))
                        .toList())
                .orElse(new ArrayList<>());
    }


    private List<Predicate> addName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        List<Predicate> restrictions = new ArrayList<>();

        String name = getSingleParameter(fields, NAME);
        if (name != null) {
            restrictions.add(criteriaBuilder.equal(root.get(NAME), name));
        }
        return restrictions;
    }

    private List<Predicate> addPartOfName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        return getPredicates(fields, criteriaBuilder, root, PART_OF_NAME, NAME);
    }

    private List<Predicate> addPartOfDescription(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root) {
        return getPredicates(fields, criteriaBuilder, root, PART_OF_DESCRIPTION, DESCRIPTION);
    }
    private void addSortByName(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                 CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> root) {

        addSorting(fields, criteriaBuilder, criteriaQuery, root, SORT_BY_NAME, NAME);
    }

    private void addSortByCreateDate(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder,
                                       CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> root) {
        addSorting(fields, criteriaBuilder, criteriaQuery, root, SORT_BY_CREATE_DATE, CREATE_DATE);
    }

    private void addSorting(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, CriteriaQuery<GiftCertificate> criteriaQuery, Root<GiftCertificate> root, String sortByCreateDate, String createDate) {
        String sortType = getSingleParameter(fields, sortByCreateDate);
        if (sortType != null) {
            if (Objects.equals(sortType, DESC)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(createDate)));
            }
            if (Objects.equals(sortType,ASC)) {
                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(createDate)));
            }
        }
    }
    private List<Predicate> getPredicates(MultiValueMap<String, String> fields, CriteriaBuilder criteriaBuilder, Root<GiftCertificate> root, String partOfDescription2, String description) {
        List<Predicate> restrictions = new ArrayList<>();

        String partOfDescription = getSingleParameter(fields, partOfDescription2);
        if (partOfDescription != null) {
            restrictions.add(criteriaBuilder.like(root.get(description), PERCENT + partOfDescription + PERCENT));
        }
        return restrictions;
    }

    private String getSingleParameter(MultiValueMap<String, String> fields, String parameter) {
        return fields.getFirst(parameter);
    }
}
