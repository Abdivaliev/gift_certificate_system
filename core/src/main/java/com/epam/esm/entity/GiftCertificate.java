package com.epam.esm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@NoArgsConstructor
@Entity
@Table(name = "gift_certificates")
public class GiftCertificate extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "gift_certificates_tags",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags;
}
