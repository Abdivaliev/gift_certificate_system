package com.epam.esm.dao.impl;

import com.epam.esm.dao.AuditDao;
import com.epam.esm.entity.Audit;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuditDaoImpl implements AuditDao {
    private final SessionFactory sessionFactory;

    @Override
    public void save(Audit audit) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(audit);
    }
}
