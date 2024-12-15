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
public class Commentary extends PanacheEntityBase {
    @Id
    @GeneratedValue
    private Long id;

    private String content;

    private Long postId;

    private Long userId;

    private Long commentaryToId;

    private Instant createdDate = Instant.now();

    private Instant updatedDate;
}
