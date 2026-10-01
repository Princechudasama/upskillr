package com.backend.dto.skill;

import com.backend.entity.SkillLevel;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserSkillRequest {

    @NotNull(message = "Skill ID is required")
    private Long skillId;

    @Size(max = 1000, message = "Skill description must be at most 1000 characters")
    private String description;

    @NotNull(message = "Current skill level is required")
    private SkillLevel currentLevel;

    @NotNull(message = "Target skill level is required")
    private SkillLevel targetLevel;

    @NotNull(message = "Available hours per week is required")
    @DecimalMin(value = "0.5", message = "Available hours must be at least 0.5")
    @DecimalMax(value = "168", message = "Available hours cannot exceed 168")
    private BigDecimal availableHoursPerWeek;
}