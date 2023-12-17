package com.epam.esm.dao.impl;

import com.epam.esm.dao.AbstractDao;
import com.epam.esm.dao.CRDDao;
import com.epam.esm.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends AbstractDao<User> implements CRDDao<User> {
    public UserDaoImpl() {
        super(User.class);
    }
}
