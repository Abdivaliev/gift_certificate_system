package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exceptions.DaoException;
import org.springframework.util.MultiValueMap;

import java.util.List;
import java.util.Map;
/**
 * Interface {@code TagService} describes abstract behavior for working with {@link Tag} objects.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
public interface TagService extends CRDService<Tag> {
}
