package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.TypeExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeExamRepository extends JpaRepository<TypeExamEntity, Long> {
}
