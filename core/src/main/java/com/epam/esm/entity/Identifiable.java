package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
/**
 * Abstract class representing an identifiable entity.
 * This class is used as a base class for entities that have an ID.
 *
 * @author Sarvar
 * @version 1.0
 * @since 2023-12-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Identifiable {
    private Long id;
}
