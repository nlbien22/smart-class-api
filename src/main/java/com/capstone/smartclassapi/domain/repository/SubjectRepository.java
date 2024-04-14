package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.SubjectEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    @Query(value = """
            SELECT s.* FROM subject s
            JOIN user_subject us ON s.subject_id = us.subject_id
            WHERE us.user_id = :currentUserId AND s.subject_id = :subjectId
            """, nativeQuery = true)
    Optional<SubjectEntity> findSubjectById(Long currentUserId, Long subjectId);

    @Query(value = """
            SELECT s.* FROM subject s
            JOIN user_subject us ON s.subject_id = us.subject_id
            WHERE us.user_id = :userId AND LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<SubjectEntity> findAllSubjectsOfUser(Long userId, String keyword, Pageable pageable);

    @Query(value = """
            SELECT count(s)
            FROM subject s
            JOIN user_subject us ON s.subject_id = us.subject_id
            WHERE us.user_id = :userId AND LOWER(s.subject_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    long countByUserId(Long userId, String keyword);

    @Query(value = """
            SELECT s.* FROM subject s
            JOIN user_subject us ON s.subject_id = us.subject_id
            WHERE us.user_id = :userId AND LOWER(s.subject_name) = LOWER(:subjectName)
            """, nativeQuery = true)
    Optional<SubjectEntity> findBySubjectName(Long userId, String subjectName);
}
