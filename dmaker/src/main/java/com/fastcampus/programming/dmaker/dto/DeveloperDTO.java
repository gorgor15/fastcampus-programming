package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDTO {
    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private String memberID;

    public static DeveloperDTO fromEntity(Developer developer){
        return DeveloperDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberID(developer.getMemberId())
                .build();

    }
}
