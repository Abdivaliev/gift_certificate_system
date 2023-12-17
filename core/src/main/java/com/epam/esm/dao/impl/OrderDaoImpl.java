package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.Pageable;
import com.epam.esm.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl extends AbstractDao<Order> implements OrderDao {
    private static final String FIND_ALL_BY_USER_ID_QUERY = "SELECT o FROM " + Order.class.getName()
            + " o WHERE o.user.id = :user_id";
    @PersistenceContext
    public EntityManager entityManager;

    public OrderDaoImpl() {
        super(Order.class);
    }


    @Override
    public List<Order> findAllByUserId(long userId, Pageable pageable) {
        return entityManager.createQuery(FIND_ALL_BY_USER_ID_QUERY, entityType)
                .setParameter("user_id", userId)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }


}
