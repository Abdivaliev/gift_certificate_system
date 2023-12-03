package com.epam.esm.convertor.extractor;

import java.util.Map;


public interface FieldExtractor<T> {
    Map<String, String> extract(T item);
}
