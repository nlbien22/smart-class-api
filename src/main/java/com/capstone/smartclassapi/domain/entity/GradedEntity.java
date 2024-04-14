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
@Table(name = "graded", schema = "public")
public class GradedEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gradedId;
    private Long userCode;
    private Float score;
    private String imageKey;
    private String nameKey;
    private LocalDateTime gradedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "key_id")
    private KeyEntity key;
}
