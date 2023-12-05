package com.epam.esm.convertor.extractor;

import java.util.Map;

/**
 * Interface for extracting fields from an item.
 * This interface is used to define a method for extracting fields from an item of type T.
 */

public interface FieldExtractor<T> {

    Map<String, String> extract(T item);
}
