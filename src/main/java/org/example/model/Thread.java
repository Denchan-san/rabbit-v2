package org.example.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Instant;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
public class Thread extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String title;

    private String description;

    private byte[] image;

    private Long userId;

    private Instant createdDate = Instant.now();
}
