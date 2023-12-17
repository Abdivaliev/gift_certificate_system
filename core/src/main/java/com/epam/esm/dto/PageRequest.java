package com.epam.esm.dto;

import com.epam.esm.exception.ExceptionMessageKey;
import com.epam.esm.exception.ExceptionResult;
import com.epam.esm.exception.IncorrectParameterException;
import lombok.Getter;

@Getter
public class PageRequest {
    private final int offset;
    private final int pageSize;

    public PageRequest(int page, int size) {
        if (page < 1 || size < 0) {
            ExceptionResult exceptionResult = new ExceptionResult();
            exceptionResult.addException(ExceptionMessageKey.INVALID_PAGINATION, page,size);
            throw new IncorrectParameterException(exceptionResult);
        }
        this.offset = (page - 1) * size;
        this.pageSize = size;
    }

}
