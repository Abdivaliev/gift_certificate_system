package com.epam.esm.convertor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class TagDTO {
    @JsonProperty("f1")
    private int id;
    @JsonProperty("f2")
    private String name;
}
