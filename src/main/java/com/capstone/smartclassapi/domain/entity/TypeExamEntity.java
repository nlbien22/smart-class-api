package com.capstone.smartclassapi.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "type_exam")
public class TypeExamEntity {
    @Id
    @Column(name = "type_exam_id")
    private Long typeExamId;

    @Column(name = "type_exam_name")
    private String typeExamName;
}