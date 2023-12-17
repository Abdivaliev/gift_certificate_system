package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.writer.QueryWriter;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import java.util.List;

@Repository
public class GiftCertificateDaoImpl extends AbstractDao<GiftCertificate> implements GiftCertificateDao {
    @PersistenceContext
    private EntityManager entityManager;
    private final QueryWriter queryWriter;

    public GiftCertificateDaoImpl(QueryWriter queryWriter) {
        super(GiftCertificate.class);
        this.queryWriter = queryWriter;
    }


    @Override
    public GiftCertificate update(GiftCertificate item) {

        return entityManager.merge(item);
    }

    @Override
    public List<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, PageRequest pageableDTo) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryWriter.writeGetQueryWithParam(fields, criteriaBuilder);

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(pageableDTo.getOffset())
                .setMaxResults(pageableDTo.getPageSize())
                .getResultList();
    }
}
