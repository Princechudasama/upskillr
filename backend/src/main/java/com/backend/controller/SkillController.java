package com.backend.controller;

import com.backend.dto.skill.CreateUserSkillRequest;
import com.backend.dto.skill.SkillResponse;
import com.backend.dto.skill.UserSkillResponse;
import com.backend.entity.User;
import com.backend.repository.UserRepository;
import com.backend.service.SkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skills")
@RequiredArgsConstructor
public class SkillController {

    private final SkillService skillService;
    private final UserRepository userRepository;

    @GetMapping
    public ResponseEntity<List<SkillResponse>> getAllSkills() {
        return ResponseEntity.ok(skillService.getAllSkills());
    }

    @PostMapping("/me")
    public ResponseEntity<UserSkillResponse> addSkill(Authentication authentication, @Valid @RequestBody CreateUserSkillRequest request) {

        Long userId = getAuthenticatedUserId(authentication);

        UserSkillResponse response =
                skillService.addSkillToUser(userId, request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/me")
    public ResponseEntity<List<UserSkillResponse>> getMySkills(
            Authentication authentication
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        return ResponseEntity.ok(
                skillService.getUserSkills(userId)
        );
    }

    @DeleteMapping("/me/{userSkillId}")
    public ResponseEntity<Void> removeSkill(
            Authentication authentication,
            @PathVariable Long userSkillId
    ) {

        Long userId = getAuthenticatedUserId(authentication);

        skillService.removeUserSkill(userId, userSkillId);

        return ResponseEntity.noContent().build();
    }

    private Long getAuthenticatedUserId(
            Authentication authentication
    ) {

        String email = authentication.getName();

        return userRepository.findByEmail(email)
                .map(User::getId)
                .orElseThrow(() ->
                        new IllegalArgumentException("Authenticated user not found")
                );
    }
}