package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<UserEntity, Long> {
    @Query(value = """
        SELECT u.*
        FROM public.user u
        INNER JOIN user_class uc ON u.user_id = uc.user_id
        WHERE uc.class_id = :classId
        AND u.user_id = :studentId
        """, nativeQuery = true)
    Optional<UserEntity> findStudentById(Long classId, Long studentId);
    @Query(value = """
        SELECT u.*
        FROM public.user u
        INNER JOIN user_class uc ON u.user_id = uc.user_id
        WHERE uc.class_id = :classId
        AND LOWER(u.full_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND uc.user_id IN (
            SELECT u.user_id
            FROM public.user u
            WHERE u.role = 'STUDENT')
        """, nativeQuery = true)
    List<UserEntity> findAllStudents(Long classId, String keyword, Pageable pageable);

    @Query(value = """
        SELECT count(u)
        FROM public.user u
        INNER JOIN user_class uc ON u.user_id = uc.user_id
        WHERE uc.class_id = :classId
        AND LOWER(u.full_name) LIKE LOWER(CONCAT('%', :keyword, '%'))
        AND uc.user_id IN (
            SELECT u.user_id
            FROM public.user u
            WHERE u.role = 'STUDENT')
        """, nativeQuery = true)
    long countAllStudents(Long classId, String keyword);

//    @Query(value = """
//        SELECT CASE WHEN COUNT(u) > 0
//        THEN true
//        ELSE false
//        END FROM user u WHERE u.email = :studentEmail
//        """, nativeQuery = true)
    boolean existsByEmail(String studentEmail);

//    @Query(value = """
//        SELECT CASE WHEN COUNT(u) > 0
//        THEN true
//        ELSE false
//        END FROM user u WHERE u.user_code = :userCode
//        """, nativeQuery = true)
    boolean existsByUserCodeAndUserCodeIsNotNull(String userCode);

    UserEntity findByEmail(String studentEmail);

    UserEntity findByUserCodeAndUserCodeIsNotNull(String userCode);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE FROM user_class uc
        WHERE  uc.class_id = :classId
        AND uc.user_id = :studentId
        AND uc.user_id IN (
            SELECT u.user_id
            FROM public.user u
            WHERE u.role = 'STUDENT')
        """, nativeQuery = true)
    void deleteByStudentId(Long classId, Long studentId);

    @Transactional
    @Modifying
    @Query(value = """
        DELETE FROM user_class uc
        WHERE uc.class_id = :classId
        AND uc.user_id IN (
            SELECT u.user_id
            FROM public.user u
            WHERE u.role = 'STUDENT')
        """, nativeQuery = true)
    void deleteAllByClassId(Long classId);
}
