package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.AnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AnswerRepository extends JpaRepository<AnswerEntity, Long> {

    @Query(value = "SELECT * FROM public.answer a WHERE a.question_id = :questionId", nativeQuery = true)
    List<AnswerEntity> findByQuestionId(Long questionId);
}
