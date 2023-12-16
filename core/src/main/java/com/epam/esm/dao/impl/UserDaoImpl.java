package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.CRDDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements CRDDao<User> {
    protected UserDaoImpl() {
        super(User.class);
    }
}
