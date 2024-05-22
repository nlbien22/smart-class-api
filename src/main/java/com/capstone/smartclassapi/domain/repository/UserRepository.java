package com.capstone.smartclassapi.domain.repository;

import com.capstone.smartclassapi.domain.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    @Query(value = """
        SELECT u.* FROM public.user u
        WHERE u.user_id = :userId
        """, nativeQuery = true)
    Optional<UserEntity> findByUserId(Long userId);
}
