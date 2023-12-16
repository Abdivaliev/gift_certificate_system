package com.epam.esm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

/**
 * Class representing a Tag entity.
 * This class has id and name fields.
 */
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "tags")
@Audited
public class Tag extends BaseEntity {
    private String name;
}
