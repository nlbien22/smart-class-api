package com.capstone.smartclassapi.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "clo", schema = "public")
public class CloEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cloId;

    private String cloTitle;

    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "subject_id")
    private SubjectEntity subject;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
