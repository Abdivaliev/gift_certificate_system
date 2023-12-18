package com.epam.esm.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class MostUsedTagDto {
    private Long userId;
    private Long tagId;
    private String tagName;
    private Long tagCount;
    private BigDecimal totalCost;
}
