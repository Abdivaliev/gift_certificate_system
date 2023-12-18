package com.epam.esm.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

import static java.time.LocalDateTime.now;

@Data
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "crated_date",updatable = false)
    private LocalDateTime createdDate;

    @Column(name = "updated_date")
    private LocalDateTime updatedDate;




    @PrePersist
    public void createDate() {
        this.createdDate = now();
    }

    @PreUpdate
    public void updateDate() {
        this.updatedDate = now();
    }

}

