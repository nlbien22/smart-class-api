package com.capstone.smartclassapi.classes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class, Long> {

    @Query(value = """
            SELECT c.* FROM classes c
            INNER JOIN users_classes uc on c.class_id = uc.class_id
            WHERE c.class_id = :classId AND uc.id = :userId
            """, nativeQuery = true)
    Optional<Class> findByClassId(Long userId, Long classId);

    @Query(value = """
            SELECT c.* FROM classes c
            INNER JOIN users_classes uc on c.class_id = uc.class_id
            WHERE class_name = :className AND uc.id = :userId
            """, nativeQuery = true)
    Optional<Class> findByClassName(Long userId, String className);

    @Query(value = """
            SELECT c.* FROM classes c
            INNER JOIN users_classes uc ON c.class_id = uc.class_id
            WHERE uc.id = :userId
            """, nativeQuery = true)
    List<Class> findAllClassOfUser(Long userId);

    @Query(value = """
            SELECT c.* FROM classes c
            INNER JOIN users_classes uc ON c.class_id = uc.class_id
            WHERE uc.id = :userId
            """, nativeQuery = true)
    Page<Class> findAllClassOfUser(Long userId, Pageable pageable);

    @Query(value = """
            SELECT c.* FROM classes c
            INNER JOIN users_classes uc ON c.class_id = uc.class_id
            WHERE uc.id = :userId AND LOWER(c.class_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
            """, nativeQuery = true)
    List<Class> findAllClassOfUserByName(Long userId, String keyword, Pageable pageable);

    @Query(value = """
        SELECT count(c) 
        FROM classes c 
        INNER JOIN users_classes uc ON c.class_id = uc.class_id
        WHERE uc.id = :userId AND LOWER(c.class_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        """, nativeQuery = true)
    long countByClassName(Long userId, @Param("keyword") String keyword);
}
