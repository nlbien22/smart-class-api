package com.capstone.smartclassapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

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
    private String userCode;
    private String keyCode;
    private String className;
    private String studentName;
    private int isError;
    private int correct;
    private int total;
    private Float score;
    private String imageKey;
    private String nameKey;
    private LocalDateTime gradedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "key_id")
    private KeyEntity key;

    @OneToMany(mappedBy = "graded", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<ResultEntity> results;

    @PrePersist
    public void prePersist() {
        gradedDate = LocalDateTime.now();
    }

    public String toString() {
        return "GradedEntity(gradedId=" + this.getGradedId() + ", userCode=" + this.getUserCode() + ", keyCode=" + this.getKeyCode() + ", className=" + this.getClassName() + ", studentName=" + this.getStudentName() + ", isError=" + this.getIsError() + ", correct=" + this.getCorrect() + ", total=" + this.getTotal() + ", score=" + this.getScore() + ", imageKey=" + this.getImageKey() + ", nameKey=" + this.getNameKey() + ", gradedDate=" + this.getGradedDate() + ")";
    }

}
