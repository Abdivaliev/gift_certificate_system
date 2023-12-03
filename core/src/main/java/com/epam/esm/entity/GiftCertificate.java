package com.epam.esm.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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

    public void setCreatedDate(LocalDateTime createdDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        createdDate.format(dateTimeFormatter);
        this.createdDate = String.valueOf(createdDate);
    }

    public void setUpdatedDate(LocalDateTime updatedDate) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        updatedDate.format(dateTimeFormatter);
        this.updatedDate = String.valueOf(updatedDate);
    }
}
