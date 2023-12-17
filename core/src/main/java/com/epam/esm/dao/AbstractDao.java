package com.epam.esm.dao;

import com.epam.esm.dto.PageRequest;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public abstract class AbstractDao<T> {
    @PersistenceContext
    protected  EntityManager entityManager;
    protected final Class<T> entityType;

    protected AbstractDao(Class<T> entityType) {
        this.entityType = entityType;
    }

    public Optional<T> findById(long id) {
        return Optional.ofNullable(entityManager.find(entityType, id));
    }

    public List<T> findAll(PageRequest pageableDTo) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() + " e", entityType)
                .setFirstResult(pageableDTo.getOffset())
                .setMaxResults(pageableDTo.getPageSize())
                .getResultList();
    }

    public void deleteById(long id) {
        T entity = entityManager.find(entityType, id);
        entityManager.remove(entity);
    }

    public T save(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    public Optional<T> findByName(String name) {
        return entityManager.createQuery("SELECT e FROM " + entityType.getSimpleName() +
                        " e WHERE e.name = :name", entityType)
                .setParameter("name", name)
                .getResultList().stream()
                .findFirst();
    }
}
