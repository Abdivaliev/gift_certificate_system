package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class UserDaoImpl implements UserDao {
    private static final String FIND_ALL_QUERY = "SELECT u FROM User u";
    private static final String FIND_BY_USERNAME = "SELECT u FROM User u WHERE u.username=:username";

    private final SessionFactory sessionFactory;

    @Override
    public Optional<User> findById(long id) {
        Session session = sessionFactory.getCurrentSession();
        return Optional.ofNullable(session.get(User.class, id));
    }

    @Override
    public List<User> findAll(PageRequest pageRequest) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_ALL_QUERY, User.class)
                .setFirstResult(pageRequest.getOffset())
                .setMaxResults(pageRequest.getPageSize())
                .getResultList();
    }

    @Override
    public User save(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(user);
        return user;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery(FIND_BY_USERNAME, User.class)
                .setParameter("username", username)
                .getResultList()
                .stream()
                .findFirst();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }
}
