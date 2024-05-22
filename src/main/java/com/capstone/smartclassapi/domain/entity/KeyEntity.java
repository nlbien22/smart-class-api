package com.capstone.smartclassapi.domain.entity;

import com.capstone.smartclassapi.domain.entity.enums.TypeKey;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
@Table(name = "key", schema = "public")
public class KeyEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long keyId;

    @Column(name = "key_code", nullable = false)
    private String keyCode;

    @Enumerated(EnumType.STRING)
    private TypeKey typeKey;

    @OneToMany(mappedBy = "key", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<AutoQuestionEntity> autoQuestions;

    private LocalDateTime createdAt;

    @ManyToMany
    @JoinTable(
            name = "exam_key",
            joinColumns = @JoinColumn(name = "key_id"),
            inverseJoinColumns = @JoinColumn(name = "exam_id")
    )
    private List<ExamEntity> exams;

    @OneToMany(mappedBy = "key", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<GradedEntity> gradedEntities;

    @PrePersist
    public void prePersist() {
        createdAt = LocalDateTime.now();
    }

    public String toString(){
        String serialized ="";
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            serialized = objectMapper.writeValueAsString(this);
        }catch(JsonProcessingException jpe){
            jpe.printStackTrace();
        }
        return serialized;
    }
}
