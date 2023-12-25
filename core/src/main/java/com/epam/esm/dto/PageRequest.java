package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageRequest {
    private int offset;
    private int pageSize;

    public static PageRequest of(int page, int size) {
        if (page < 1 || size < 0) {
            ExceptionResult exceptionResult = new ExceptionResult();
            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page, size);
            throw new IncorrectParameterException(exceptionResult);
        }
        int offset = (page - 1) * size;
        return new PageRequest(offset, size);
    }
}