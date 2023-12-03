package com.epam.esm.convertor.extractor;

import java.util.Map;

/**
 * Interface for extracting fields from an item.
 * This interface is used to define a method for extracting fields from an item of type T.
 *
 * @param <T> The type of the item to extract fields from.
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */

public interface FieldExtractor<T> {
    /**
     * Extracts fields from an item.
     *
     * @param item The item to extract fields from.
     * @return A map with the extracted fields. The keys are the field names and the values are the field values.
     */
    Map<String, String> extract(T item);
}
