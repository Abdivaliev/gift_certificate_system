package com.epam.esm.constant;

import lombok.Getter;

@Getter
public enum TokenType {
    ACCESS_TOKEN("access-token", 1000 * 60 * 3),
    REFRESH_TOKEN("refresh-token", 1000 * 60 * 6);
    private final String name;
    private final int duration;

    TokenType(String name, int duration) {
        this.name = name;
        this.duration = duration;
    }
}
