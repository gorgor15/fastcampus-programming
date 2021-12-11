package com.fastcampus.programming.dmaker.type;

import com.fastcampus.programming.dmaker.Constant.DMakerConstant;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NOT_MATCHED;

/**
 * @author Snow
 */

@AllArgsConstructor
@Getter
public enum DeveloperLevel {
//    NEW("신입 개발자",0,0),
//    JUNIOR("주니어 개발자",1, DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS),
//    JUNGNIOR("중니어 개발자",DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS+1,DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS-1),
//    SENIOR("시니어 개발자",DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS,70);
//
//    private final String description;
//    private final Integer minExperienceYears;
//    private final Integer maxExperienceYears;

    NEW("신입 개발자",years-> years==0),
    JUNIOR("주니어 개발자",years -> years <=DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS),
    JUNGNIOR("중니어 개발자",years->years> DMakerConstant.MAX_JUNIOR_EXPERIENCE_YEARS
        && years<DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS),
    SENIOR("시니어 개발자",years-> years>= DMakerConstant.MIN_SENIOR_EXPERIENCE_YEARS);

    private final String description;
    private final Function<Integer,Boolean> validateFunction;

    public void validateExperienceYears(Integer years){
        if(validateFunction.apply(years))
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
    }
}
