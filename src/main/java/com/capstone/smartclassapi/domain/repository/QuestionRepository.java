package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {

    @Query(value = """
            SELECT * FROM public.question q
            WHERE q.clo_id = :cloId
            """, nativeQuery = true)
    List<QuestionEntity> findAllQuestionsByCloId(Long cloId);
}
