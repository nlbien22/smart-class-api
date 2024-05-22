package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.ExamEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<ExamEntity, Long> {

    @Query(value = """
            SELECT e.* FROM exam e
            INNER JOIN user_exam ue ON e.exam_id = ue.exam_id
            WHERE ue.user_id = :userId AND e.exam_id = :examId
            """, nativeQuery = true)
    Optional<ExamEntity> findExamByUserId(Long userId, Long examId);

    @Query(value = """
            SELECT e.* FROM exam e
            WHERE e.class_id = :classId AND e.exam_id = :examId
            """, nativeQuery = true)
    Optional<ExamEntity> findExamByClassId(Long classId, Long examId);

    @Query(value = """
            SELECT e.* FROM exam e
            INNER JOIN user_exam ue ON e.exam_id = ue.exam_id
            WHERE ue.user_id = :userId AND LOWER(e.exam_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<ExamEntity> findAllExamsByUserId(Long userId, String keyword, Pageable pageable);

    @Query(value = """
            SELECT e.* FROM exam e
            WHERE e.class_id = :classId  AND LOWER(e.exam_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<ExamEntity> findAllExamsByClassId(Long classId, String keyword, Pageable pageable);

    @Query(value = """
            SELECT count(e) 
            FROM exam e
            INNER JOIN user_exam ue ON e.exam_id = ue.exam_id
            WHERE ue.user_id = :userId AND LOWER(e.exam_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    long countExamNameByUserId(Long userId, String keyword);

    @Query(value = """
            SELECT count(e) 
            FROM exam e
            WHERE e.class_id = :classId AND LOWER(e.exam_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    long countExamNameByClassId(Long classId, String keyword);

    @Transactional
    @Modifying
    @Query(value = """
            DELETE FROM exam e
            WHERE e.class_id = :classId AND e.exam_id IN (
                SELECT ue.exam_id
                FROM user_exam ue
                WHERE ue.user_id = :userId
            )
            """, nativeQuery = true)
    void deleteAllExamsByClassId(Long userId, Long classId);
}
