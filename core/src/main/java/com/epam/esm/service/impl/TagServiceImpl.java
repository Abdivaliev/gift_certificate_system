package com.epam.esm.service.impl;


import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.repo.CRDDao;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Class {@code TagServiceImpl} is implementation of interface {@link TagService} and intended to work with {@link Tag} objects.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {


    @Autowired
    public TagServiceImpl(CRDDao<Tag> dao) {
        super(dao);
    }

    @Override
    public void save(Tag tag) throws DaoException {
        dao.save(tag);
    }

}
