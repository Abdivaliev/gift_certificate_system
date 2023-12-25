package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.Order;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderDaoImpl implements OrderDao {
    private static final String FIND_ALL_BY_USER_ID_QUERY = "SELECT o FROM Order o WHERE o.user.id = :user_id";
    private static final String FIND_ALL_QUERY = "SELECT o FROM Order o";
    private final SessionFactory sessionFactory;



    @Override
    public List<Order> findAllByUserId(long userId, PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_BY_USER_ID_QUERY, Order.class)
                .setParameter("user_id", userId)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public Optional<Order> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(Order.class, id));
    }

    @Override
    public List<Order> findAll(PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_QUERY, Order.class)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public Order save(Order order) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(order);
        return order;
    }

    @Override
    public void deleteById(long id) {
        Session session = sessionFactory.getCurrentSession();
        Order order = session.byId(Order.class).load(id);
        session.remove(order);
    }
}
