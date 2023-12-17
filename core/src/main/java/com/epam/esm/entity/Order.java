package com.epam.esm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "orders")
public class Order extends BaseEntity {

    private BigDecimal purchaseCost;
    @ManyToOne
    private User user;
    @ManyToOne
    private GiftCertificate giftCertificate;
}

