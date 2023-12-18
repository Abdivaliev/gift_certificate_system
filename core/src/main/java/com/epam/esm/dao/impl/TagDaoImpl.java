package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String FIND_MOST_WIDELY_USED_TAG = "WITH UserTagCosts AS (SELECT u.id         AS user_id,\n" +
            "                             t.id         as tag_id,\n" +
            "                             t.name       AS tag_name,\n" +
            "                             COUNT(*)     AS tag_count,\n" +
            "                             SUM(uo.purchasecost) AS total_cost\n" +
            "                      FROM users u\n" +
            "                               JOIN orders uo ON u.id = uo.user_id\n" +
            "                               JOIN gift_certificates gc ON uo.giftcertificate_id = gc.id\n" +
            "                               JOIN gift_certificates_tags gct ON gc.id = gct.gift_certificate_id\n" +
            "                               JOIN tags t ON gct.tag_id = t.id\n" +
            "                      WHERE u.id = :userId\n" +
            "                      GROUP BY u.id, t.name,t.id)\n" +
            "SELECT user_id,\n" +
            "       tag_id,\n" +
            "       tag_name,\n" +
            "       tag_count,\n" +
            "       total_cost\n" +
            "FROM UserTagCosts\n" +
            "WHERE (tag_count, total_cost) = (SELECT MAX(tag_count),\n" +
            "                                        MAX(total_cost)\n" +
            "                                 FROM UserTagCosts);";


    @PersistenceContext
    protected EntityManager entityManager;

    protected TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public Map<List<?>, List<?>> findMostUsedTagByUserId(Long userId) {

        List<?> resultList = entityManager.createNativeQuery(FIND_MOST_WIDELY_USED_TAG, Tuple.class)
                .setParameter("userId", userId)
                .getResultList();

        List<?> explanations = entityManager.createNativeQuery("EXPLAIN " + FIND_MOST_WIDELY_USED_TAG, Tuple.class)
                .setParameter("userId", userId)
                .getResultList();

        Map<List<?>, List<?>> result = new HashMap<>();
        result.put(resultList, explanations);

        return result;
    }
}
