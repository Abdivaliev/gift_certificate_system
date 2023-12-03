package com.epam.esm.entity;

import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Tag extends Identifiable {

    private String name;

    public Tag(long tagId, String tagName) {
        super(tagId);
        this.name=tagName;
    }
}
