package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.ExamEntity;
import com.capstone.smartclassapi.domain.entity.KeyEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface KeyRepository extends JpaRepository<KeyEntity, Long>{

    @Query(value = """
            SELECT k.* FROM key k
            INNER JOIN exam_key ek ON k.key_id = ek.key_id
            WHERE ek.exam_id = :examId AND k.key_code = :keyCode
            """, nativeQuery = true)
    Optional<KeyEntity> findByKeyCode(Long examId, String keyCode);

    @Query(value = """
            SELECT k.* FROM key k
            INNER JOIN exam_key ek ON k.key_id = ek.key_id
            WHERE ek.exam_id = :examId AND k.key_id = :keyId
            """, nativeQuery = true)
    Optional<KeyEntity> findKeyById(Long examId, Long keyId);

    @Query(value = """
            SELECT k.* FROM key k
            INNER JOIN exam_key ek ON k.key_id = ek.key_id
            WHERE ek.exam_id = :examId
            """, nativeQuery = true)
    List<KeyEntity> findAllKeysByExamId(Long examId, Pageable pageable);

    @Query(value = """
            SELECT count(k)
            FROM key k
            INNER JOIN exam_key ek ON k.key_id = ek.key_id
            WHERE ek.exam_id = :examId
            """, nativeQuery = true)
    long countKeyByExamId(Long examId);

//    @Query(value = """
//            SELECT  FROM key k
//            INNER JOIN auto_question aq ON k.key_id = aq.key_id
//            WHERE aq.exam_id = :examId AND k.key_code = :keyCode
//            """, nativeQuery = true)
//    Optional<?> findKeyResponse(Long examId, Long keyCode);
}
