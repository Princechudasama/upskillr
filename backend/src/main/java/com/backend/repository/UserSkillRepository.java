package com.backend.repository;

import com.backend.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {

    List<UserSkill> findByUserIdOrderByCreatedAtDesc(Long userId);

    Optional<UserSkill> findByIdAndUserId(Long id, Long userId);

    boolean existsByUserIdAndSkillId(Long userId, Long skillId);
}