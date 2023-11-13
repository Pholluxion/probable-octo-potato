package com.phollux.tupropiedad.image.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;


@Entity
@Table(name = "Images")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Image {

    @Id
    @Column(nullable = false, updatable = false)
    @GenericGenerator(name = "uuid", type = org.hibernate.id.uuid.UuidGenerator.class)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false)
    private Long size;

    @Column(nullable = false, length = 110)
    private String type;

    @Lob
    private byte[] imageData;

}
