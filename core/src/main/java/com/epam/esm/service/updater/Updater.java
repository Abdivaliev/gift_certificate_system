package com.epam.esm.service.updater;

import java.util.Set;

public interface Updater<T> {
    T updateObject(T newEntity, T oldEntity);

    Set<T> updateListFromDatabase(Set<T> newList);
}
