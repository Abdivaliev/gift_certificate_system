package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.TagDto;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL_QUERY = "SELECT t FROM Tag t";
    private static final String FIND_BY_NAME_QUERY = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String FIND_MOST_WIDELY_USED_TAG_LIST = "WITH UserTagCosts AS (\n" +
            "    SELECT t.id as tagId,t.name AS tagName,t.crated_date AS createdDate,t.updated_date AS updatedDate,\n" +
            "        COUNT(*) AS tag_count,\n" +
            "        SUM(uo.purchasecost) AS total_cost\n" +
            "    FROM users u\n" +
            "JOIN orders uo ON u.id = uo.user_id\n" +
            "JOIN gift_certificates gc ON uo.giftcertificate_id = gc.id\n" +
            "JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id\n" +
            "JOIN tags t ON gct.tag_id = t.id\n" +
            "    WHERE\n" +
            "            u.id = :userId\n" +
            "    GROUP BY\n" +
            "        u.id, t.name, t.id, t.crated_date, t.updated_date\n" +
            ")\n" +
            "SELECT tagId, tagName, createdDate, updatedDate\n" +
            "FROM\n" +
            "    UserTagCosts\n" +
            "WHERE (tag_count, total_cost) = (SELECT MAX(tag_count), MAX(total_cost)\n" +
            "        FROM UserTagCosts);";


    private final SessionFactory sessionFactory;
    private final Converter<Tag, Tuple> converter;


    @Override
    public Optional<Tag> findByName(String name) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_NAME_QUERY, Tag.class)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }


    @Override
    public List<Tag> findMostUsedTagListByUserId(Long userId,PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createNativeQuery(FIND_MOST_WIDELY_USED_TAG_LIST, Tuple.class)
                .setParameter("userId", userId)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList().stream().map(converter::convertToEntity).toList();

    }

    @Override
    public Optional<Tag> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.find(Tag.class, id));
    }

    @Override
    public List<Tag> findAll(PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_QUERY, Tag.class)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public Tag save(Tag tag) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(tag);
        return tag;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Tag tag = session.find(Tag.class, id);
        session.remove(tag);
    }
}
