package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.CloEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CloRepository extends JpaRepository<CloEntity, Long> {
    @Query(value = """
            SELECT c.* FROM clo c
            WHERE c.subject_id = :subjectId AND c.clo_id = :cloId
            """, nativeQuery = true)
    Optional<CloEntity> findCloById(Long subjectId, Long cloId);

    @Query(value = """
            SELECT c.* FROM clo c
            WHERE c.subject_id = :subjectId AND LOWER(c.clo_title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<CloEntity> findAllClos(Long subjectId, String keyword, Pageable pageable);

    @Query(value = """
            SELECT c.* FROM clo c
            JOIN subject s ON c.subject_id = s.subject_id
            WHERE s.subject_id = :subjectId AND c.clo_title = :cloName
            """, nativeQuery = true)
    Optional<CloEntity> findCloByName(Long subjectId, String cloName);

    @Query(value = """
            SELECT count(c)
            FROM clo c
            WHERE c.subject_id = :subjectId AND LOWER(c.clo_title) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    long countBySubjectId(Long subjectId, String keyword);
}
