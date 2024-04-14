package com.capstone.smartclassapi.domain.entity;

import com.capstone.smartclassapi.domain.entity.enums.Level;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "question", schema = "public")
public class QuestionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Column(name = "question_content")
    private String questionContent;

    @Column(name = "question_image")
    private String questionImage;

    @Enumerated(EnumType.STRING)
    @Column(name = "level")
    private Level level;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "clo_id")
    private CloEntity clo;

    @OneToMany(mappedBy = "question", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AnswerEntity> answers;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }
}
