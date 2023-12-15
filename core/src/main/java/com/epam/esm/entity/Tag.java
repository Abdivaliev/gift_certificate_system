package com.epam.esm.entity;

import lombok.*;
/**
 * Class representing a Tag entity.
 * This class extends the BaseEntity class and has a name field.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class Tag extends BaseEntity {
    private String name;
    public Tag(long tagId, String tagName) {
        super(tagId);
        this.name=tagName;
    }
}
