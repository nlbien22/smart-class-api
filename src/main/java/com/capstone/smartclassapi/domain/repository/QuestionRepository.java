package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<QuestionEntity, Long> {
}
