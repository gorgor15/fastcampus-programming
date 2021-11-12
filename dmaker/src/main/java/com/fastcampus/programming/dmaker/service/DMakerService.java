package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.exception.DMakerErrorCode;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import javax.validation.Valid;

import java.util.Optional;

import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.DUPLICATED_MEMBER_ID;
import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.LEVEL_EXPERIENCE_YEAR_NOT_MATCHED;

// 호출하기위해 사용
@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;

    //디비를 추상화한 개념
    private final EntityManager em;
    // ACID
    // Atomic 은행에서 잘못송금되는것으로 생각
    // Consistency 커밋이 끝나는지점에서 끝나야된다. (잔고는 10000원있는데 송금하고 계좌는 0원보다 적을수없다)
    // Isolation
    // Durability 커밋이 됬다하면 커밋된이력은 남아있어야된다.(모든이력은 남아야된다.)
    //데이터베이스의 상태를 변경하는 작업 또는 한번에 수행되어야 하는 연산들을 의미한다.
    @Transactional
    public void createDeveloper(CreateDeveloper.Request request) {

            validateCreateDeveloperRequest(request);
            //business logic start
            Developer developer = Developer.builder()
                    .developerLevel(DeveloperLevel.JUNIOR)
                    .developerSkillType(DeveloperSkillType.FRONT_END)
                    .experienceYears(2)
                    .name("Olaf")
                    .age(25)
                    .build();

            //데이터베이스 저장
            /* A->B 1만원 송금 */
            //A계좌에서 1만원 줄임
            developerRepository.save(developer);
            //B계좌에서 1만원 늘림
            // developerRepository.delete(developer1);
            //business logic end


    }
    private void validateCreateDeveloperRequest(CreateDeveloper.Request request){
        Integer experienceYears = request.getExperienceYears();
        DeveloperLevel developerLevel = request.getDeveloperLevel();
        if(developerLevel ==DeveloperLevel.SENIOR
            && experienceYears <10){
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }

        if(developerLevel ==DeveloperLevel.JUNGNIOR
            &&(experienceYears <4|| experienceYears >10)){
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }

        if(developerLevel == DeveloperLevel.JUNIOR && experienceYears >4){
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }
      developerRepository.findByMemberId(request.getMemberId())
              .ifPresent((developer ->{
                  throw new DMakerException(DUPLICATED_MEMBER_ID);
              }));
    }

}
