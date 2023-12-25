package com.epam.esm.hateoas;

import org.springframework.hateoas.RepresentationModel;

public interface HateoasAdder<T extends RepresentationModel<T>> {
    void addLinks(T entity);

    void addLinks(T entity, int page, int size);
}
