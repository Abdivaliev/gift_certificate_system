package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Class representing a GiftCertificate entity.
 * This class extends the Identifiable class and has fields for name, description, price, duration, created date, updated date, and a list of tags.
 * It also includes methods to set the created and updated dates with a specific date time format.
 *
 * @author Sarvavr
 * @version 1.0
 * @since 2023-12-03
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class GiftCertificate extends Identifiable {
    private String name;
    private String description;
    private BigDecimal price;
    private Integer duration;
    private String createdDate;
    private String updatedDate;
    private List<Tag> tags;

    /**
     * Sets the created date of the gift certificate with a specific date time format.
     *
     * @param createdDate The created date to set.
     */
    public void setCreatedDate(LocalDateTime createdDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        createdDate.format(dateTimeFormatter);
        this.createdDate = String.valueOf(createdDate);
    }

    /**
     * Sets the updated date of the gift certificate with a specific date time format.
     *
     * @param updatedDate The updated date to set.
     */
    public void setUpdatedDate(LocalDateTime updatedDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        updatedDate.format(dateTimeFormatter);
        this.updatedDate = String.valueOf(updatedDate);
    }
}
