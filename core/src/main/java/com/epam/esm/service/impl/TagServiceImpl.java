package com.epam.esm.service.impl;


import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import com.epam.esm.exceptions.IncorrectParameterException;
import com.epam.esm.repo.CRDDao;
import com.epam.esm.repo.TagRepo;
import com.epam.esm.service.AbstractService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.validator.TagValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.esm.service.FilterParameters.*;

@Service
public class TagServiceImpl extends AbstractService<Tag> implements TagService {


    @Autowired
    public TagServiceImpl(CRDDao<Tag> dao) {
        super(dao);
    }

    @Override
    public void save(Tag tag) throws DaoException, IncorrectParameterException {
        dao.save(tag);
        TagValidator.validateName(tag.getName());
    }

}
