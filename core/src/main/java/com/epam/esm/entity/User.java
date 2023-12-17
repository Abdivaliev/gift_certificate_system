package com.epam.esm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Set;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Table(name = "users")
@Entity
public class User extends BaseEntity {
    private String name;

    @OneToMany(mappedBy = "user")
    private Set<Order> orders;
}