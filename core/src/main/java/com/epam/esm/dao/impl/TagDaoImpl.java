package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class TagDaoImpl extends AbstractDao<Tag> implements TagDao {
    private static final String FIND_MOST_WIDELY_USED_TAG = "SELECT t FROM "
            + "GiftCertificate g INNER JOIN g.tags t WHERE g.id IN (SELECT o.giftCertificate.id FROM Order o "
            + "WHERE o.user.id IN (SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(o.purchaseCost) DESC)) "
            + "GROUP BY t.id ORDER BY COUNT(t.id) DESC";
    @PersistenceContext
    protected EntityManager entityManager;

    protected TagDaoImpl() {
        super(Tag.class);
    }

    @Override
    public Optional<Tag> findMostUsedTag() {
        return entityManager.createQuery(FIND_MOST_WIDELY_USED_TAG, entityType)
                .setMaxResults(1)
                .getResultList().stream()
                .findFirst();
    }
}
