package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.dto.converter.Converter;
import com.epam.esm.entity.Tag;
import jakarta.persistence.Tuple;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class TagDaoImpl implements TagDao {
    private static final String FIND_ALL_QUERY = "SELECT t FROM Tag t";
    private static final String FIND_BY_NAME_QUERY = "SELECT t FROM Tag t WHERE t.name = :name";
    private static final String FIND_MOST_WIDELY_USED_TAG_LIST = """
WITH UserTagCosts AS (
    SELECT
        t.id as tagId,
        t.name AS tagName,
        COUNT(*) AS tag_count,
        SUM(uo.purchasecost) AS total_cost
    FROM
        users u
            JOIN orders uo ON u.id = uo.user_id
            JOIN gift_certificates gc ON uo.giftcertificate_id = gc.id
            JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id
            JOIN tags t ON gct.tag_id = t.id
    WHERE
            u.id = :userId
    GROUP BY
        u.id, t.name,t.id
)
SELECT
    tagId,
    tagName
FROM
    UserTagCosts
WHERE
        (tag_count, total_cost) = (
        SELECT
            MAX(tag_count),
            MAX(total_cost)
        FROM
            UserTagCosts
    );
""";


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
