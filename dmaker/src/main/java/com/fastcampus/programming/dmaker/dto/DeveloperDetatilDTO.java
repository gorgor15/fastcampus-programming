package com.fastcampus.programming.dmaker.dto;

import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.*;

//상세정보
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeveloperDetatilDTO {


    private DeveloperLevel developerLevel;
    private DeveloperSkillType developerSkillType;
    private Integer experienceYears;
    private String memberId;
    private String name;
    private Integer age;

    public static DeveloperDetatilDTO fromEntity(Developer developer){
        return DeveloperDetatilDTO.builder()
                .developerLevel(developer.getDeveloperLevel())
                .developerSkillType(developer.getDeveloperSkillType())
                .memberId(developer.getMemberId())
                .experienceYears(developer.getExperienceYears())
                .name(developer.getName())
                .age(developer.getAge())
                .build();

    }
}
