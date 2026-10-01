package com.backend.dto.skill;

import com.backend.entity.SkillLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSkillResponse {

    private Long id;

    private SkillResponse skill;

    private String description;

    private SkillLevel currentLevel;

    private SkillLevel targetLevel;

    private BigDecimal availableHoursPerWeek;
}