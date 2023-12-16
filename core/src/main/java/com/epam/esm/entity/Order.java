package com.epam.esm.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.envers.Audited;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
@Entity
@Table(name = "orders")
@Audited
public class Order extends BaseEntity {
    private LocalDateTime purchaseTime;
    private BigDecimal purchaseCost;
    @ManyToOne
    private User user;
    @ManyToOne
    private GiftCertificate giftCertificate;
}

