package com.epam.esm.dao.impl;

import com.epam.esm.dao.CRDDao;
import com.epam.esm.dto.PageRequest;
import com.epam.esm.entity.User;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserDaoImpl implements CRDDao<User> {
    private static final String FIND_ALL_QUERY = "SELECT u FROM User u";
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
        throw new UnsupportedOperationException();
    }

    @Override
    public void deleteById(long id) {
        throw new UnsupportedOperationException();
    }
}
