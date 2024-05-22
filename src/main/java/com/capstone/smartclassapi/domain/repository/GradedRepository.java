package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.GradedEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GradedRepository extends JpaRepository<GradedEntity, Long> {

    @Query(value = """
            SELECT g.* FROM graded g
            INNER JOIN key k ON g.key_id = k.key_id
            INNER JOIN exam_key ek ON ek.key_id = k.key_id
            WHERE ek.exam_id = :examId AND g.user_code = :studentId
            """, nativeQuery = true)
    GradedEntity findByExamIdAndStudentId(Long examId, String studentId);

    @Query(value = """
            SELECT g.* FROM graded g
            INNER JOIN key k ON g.key_id = k.key_id
            INNER JOIN exam_key ek ON ek.key_id = k.key_id
            WHERE ek.exam_id = :examId
            """, nativeQuery = true)
    List<GradedEntity> findByExamId(Long examId);
}
