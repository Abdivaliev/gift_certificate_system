package com.epam.esm.entity;

import lombok.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class representing a GiftCertificate entity.
 * This class extends the BaseEntity class and has fields for name, description, price, duration, created date, updated date, and a list of tags.
 * It also includes methods to set the created and updated dates with a specific date time format.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class GiftCertificate extends BaseEntity {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private Timestamp createdDate;
    private Timestamp updatedDate;
    private List<Tag> tags;

    public String getCreatedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return updatedDate.toLocalDateTime().format(dateTimeFormatter);
    }

    public String getUpdatedDate() {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        return updatedDate.toLocalDateTime().format(dateTimeFormatter);
    }

    public GiftCertificate(Long id, String name, String description, BigDecimal price, Integer duration, Timestamp createdDate, Timestamp updatedDate, List<Tag> tags) {
        super(id);
        this.name = name;
        this.description = description;
        this.price = price;
        this.duration = duration;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.tags = tags;
    }
}
