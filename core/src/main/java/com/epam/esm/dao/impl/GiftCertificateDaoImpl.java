package com.epam.esm.dao.impl;

import com.epam.esm.dao.GiftCertificateDao;
import com.epam.esm.dao.writer.QueryWriter;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.GiftCertificate;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
@RequiredArgsConstructor
public class GiftCertificateDaoImpl implements GiftCertificateDao {
    private static final String FIND_BY_TAG_NAMES_QUERY = "SELECT gc FROM GiftCertificate gc WHERE :tagCount = (SELECT COUNT(t) FROM gc.tags t WHERE t.name IN (:tagNames))";
    private static final String FIND_ALL_QUERY = "SELECT g FROM GiftCertificate g";
    private static final String FIND_BY_NAME_QUERY = "SELECT g FROM GiftCertificate g WHERE g.name=:name";

    private final SessionFactory sessionFactory;
    private final QueryWriter queryWriter;


    @Override
    public GiftCertificate update(GiftCertificate item) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(item);
    }

    @Override
    public Optional<GiftCertificate> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_NAME_QUERY, GiftCertificate.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }

    @Override
    public List<GiftCertificate> findByTagNames(Set<String> tagNames, PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_TAG_NAMES_QUERY, GiftCertificate.class)
                .setParameter("tagNames", tagNames)
                .setParameter("tagCount", (long) tagNames.size())
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public List<GiftCertificate> findWithFilters(MultiValueMap<String, String> fields, PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<GiftCertificate> criteriaQuery = queryWriter.writeGetQueryWithParam(fields, criteriaBuilder);

        return session.createQuery(criteriaQuery)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<GiftCertificate> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(GiftCertificate.class, id));
    }

    @Override
    public List<GiftCertificate> findAll(PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_QUERY, GiftCertificate.class)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public GiftCertificate save(GiftCertificate giftCertificate) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(giftCertificate);
        return giftCertificate;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        GiftCertificate entity = session.find(GiftCertificate.class, id);
        session.remove(entity);
    }
}
