package com.backend.service;

import com.backend.dto.skill.SkillResponse;
import com.backend.dto.skill.UserSkillResponse;
import com.backend.dto.skill.CreateUserSkillRequest;
import com.backend.entity.Skill;
import com.backend.entity.User;
import com.backend.entity.UserSkill;
import com.backend.repository.SkillRepository;
import com.backend.repository.UserRepository;
import com.backend.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkillService {

    private final SkillRepository skillRepository;
    private final UserRepository userRepository;
    private final UserSkillRepository userSkillRepository;

    @Transactional(readOnly = true)
    public List<SkillResponse> getAllSkills() {
        return skillRepository.findAllByOrderByNameAsc()
                .stream()
                .map(this::toSkillResponse)
                .toList();
    }

    @Transactional
    public UserSkillResponse addSkillToUser(
            Long userId,
            CreateUserSkillRequest request
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new IllegalArgumentException("User not found")
                );

        Skill skill = skillRepository.findById(request.getSkillId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Skill not found")
                );

        if (userSkillRepository.existsByUserIdAndSkillId(
                userId,
                skill.getId()
        )) {
            throw new IllegalArgumentException(
                    "User has already selected this skill"
            );
        }

        UserSkill userSkill = UserSkill.builder()
                .user(user)
                .skill(skill)
                .description(request.getDescription())
                .currentLevel(request.getCurrentLevel())
                .targetLevel(request.getTargetLevel())
                .availableHoursPerWeek(request.getAvailableHoursPerWeek())
                .build();

        UserSkill saved = userSkillRepository.save(userSkill);

        return toUserSkillResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<UserSkillResponse> getUserSkills(Long userId) {

        return userSkillRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::toUserSkillResponse)
                .toList();
    }

    @Transactional
    public void removeUserSkill(Long userId, Long userSkillId) {

        UserSkill userSkill = userSkillRepository
                .findByIdAndUserId(userSkillId, userId)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Selected skill not found"
                        )
                );

        userSkillRepository.delete(userSkill);
    }

    private SkillResponse toSkillResponse(Skill skill) {

        return SkillResponse.builder()
                .id(skill.getId())
                .name(skill.getName())
                .description(skill.getDescription())
                .build();
    }

    private UserSkillResponse toUserSkillResponse(UserSkill userSkill) {

        return UserSkillResponse.builder()
                .id(userSkill.getId())
                .skill(toSkillResponse(userSkill.getSkill()))
                .description(userSkill.getDescription())
                .currentLevel(userSkill.getCurrentLevel())
                .targetLevel(userSkill.getTargetLevel())
                .availableHoursPerWeek(
                        userSkill.getAvailableHoursPerWeek()
                )
                .build();
    }
}