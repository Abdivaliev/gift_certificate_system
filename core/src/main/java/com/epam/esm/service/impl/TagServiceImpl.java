package com.epam.esm.service.impl;


import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.dao.CRDDao;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.CRDService;
import org.springframework.stereotype.Service;

/**
 * Class {@code TagServiceImpl} is implementation of interface {@link CRDService} and intended to work with {@link Tag} objects.
 */
@Service
public class TagServiceImpl extends AbstractService<Tag> implements CRDService<Tag> {

    public TagServiceImpl(CRDDao<Tag> dao) {
        super(dao);
    }

    @Override
    public void save(Tag tag) throws DaoException {
        dao.save(tag);
    }

}
