package com.epam.esm.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

/**
 * Class representing a Tag entity.
 * This class has id and name fields.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "tags")
public class Tag extends BaseEntity {
    @Column(unique = true)
    private String name;
}
