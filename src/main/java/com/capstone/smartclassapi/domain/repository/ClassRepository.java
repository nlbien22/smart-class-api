package com.capstone.smartclassapi.domain.repository;
import com.capstone.smartclassapi.domain.entity.ClassEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClassRepository extends JpaRepository<ClassEntity, Long> {

    @Query(value = """
            SELECT c.* FROM class c
            INNER JOIN user_class uc on c.class_id = uc.class_id
            WHERE c.class_id = :classId AND uc.user_id = :userId
            """, nativeQuery = true)
    Optional<ClassEntity> findByClassId(Long userId, Long classId);

    @Query(value = """
            SELECT c.* FROM class c
            INNER JOIN user_class uc on c.class_id = uc.class_id
            WHERE class_name = :className AND uc.user_id = :userId
            """, nativeQuery = true)
    Optional<ClassEntity> findByClassName(Long userId, String className);

    @Query(value = """
            SELECT c.* FROM class c
            INNER JOIN user_class uc ON c.class_id = uc.class_id
            WHERE uc.user_id = :userId AND LOWER(c.class_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<ClassEntity> findAllClassOfUserByName(Long userId, String keyword, Pageable pageable);

    @Query(value = """
        SELECT count(c)
        FROM class c
        INNER JOIN user_class uc ON c.class_id = uc.class_id
        WHERE user_id = :userId AND LOWER(class_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """, nativeQuery = true)
    long countByClassName(Long userId, @Param("keyword") String keyword);
}
