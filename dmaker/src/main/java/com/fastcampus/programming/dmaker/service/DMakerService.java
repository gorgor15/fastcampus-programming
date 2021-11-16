package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.code.StatusCode;
import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDTO;
import com.fastcampus.programming.dmaker.dto.DeveloperDetatilDTO;
import com.fastcampus.programming.dmaker.dto.UpdateDeveloper;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.entity.RetiredDeveloper;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

import static com.fastcampus.programming.dmaker.exception.DMakerErrorCode.*;

// 호출하기위해 사용
@Service
@RequiredArgsConstructor
public class DMakerService {
    private final DeveloperRepository developerRepository;
    private final RetiredDeveloperRepository retiredDeveloperRepository;
    //디비를 추상화한 개념
    private final EntityManager em;
    // ACID
    // Atomic 은행에서 잘못송금되는것으로 생각
    // Consistency 커밋이 끝나는지점에서 끝나야된다. (잔고는 10000원있는데 송금하고 계좌는 0원보다 적을수없다)
    // Isolation
    // Durability 커밋이 됬다하면 커밋된이력은 남아있어야된다.(모든이력은 남아야된다.)
    //데이터베이스의 상태를 변경하는 작업 또는 한번에 수행되어야 하는 연산들을 의미한다.
    @Transactional
    public CreateDeveloper.Response createDeveloper(CreateDeveloper.Request request) {

            validateCreateDeveloperRequest(request);
            //business logic start
            Developer developer = Developer.builder()
                    //요청받은대로 저장이된다.
                    .developerLevel(request.getDeveloperLevel())
                    .developerSkillType(request.getDeveloperSkillType())
                    .experienceYears(request.getExperienceYears())
                    .memberId(request.getMemberId())
                    .statusCode(StatusCode.EMPLOYED)
                    .name(request.getName())
                    .age(request.getAge())
                    .build();

            //데이터베이스 저장
            /* A->B 1만원 송금 */
            //A계좌에서 1만원 줄임
            developerRepository.save(developer);
            //B계좌에서 1만원 늘림
            // developerRepository.delete(developer1);
            //business logic end

            return CreateDeveloper.Response.fromEntity(developer);

    }
    private void validateCreateDeveloperRequest(CreateDeveloper.Request request){

        validateDeveloperLevel(request.getExperienceYears(), request.getDeveloperLevel());
        developerRepository.findByMemberId(request.getMemberId())
              .ifPresent((developer ->{
                  throw new DMakerException(DUPLICATED_MEMBER_ID);
              }));
    }

    //조회하기
    public List<DeveloperDTO> getAllEmployedDevelopers() {
        return developerRepository.findDevelopersByStatusCodeEquals(StatusCode.EMPLOYED)
                .stream().map(DeveloperDTO::fromEntity)
                .collect(Collectors.toList());
    }
    
    //상세보기
    public DeveloperDetatilDTO getDeveloperDetail(String memberId) {
        return developerRepository.findByMemberId(memberId)
                .map(DeveloperDetatilDTO::fromEntity)
                .orElseThrow(()->new DMakerException(NO_DEVELOPER));
    }

    //수정하기
    @Transactional
    public DeveloperDetatilDTO UpdateDeveloper(String memberId, UpdateDeveloper.Request request) {
        validateUpdateDeveloperRequest(request,memberId);

       Developer developer = developerRepository.findByMemberId(memberId).orElseThrow(
                ()-> new DMakerException(NO_DEVELOPER)
        );

       developer.setDeveloperLevel(request.getDeveloperLevel());
       developer.setDeveloperSkillType(request.getDeveloperSkillType());
       developer.setExperienceYears(request.getExperienceYears());

       return DeveloperDetatilDTO.fromEntity(developer);
    }

    private void validateUpdateDeveloperRequest(
            UpdateDeveloper.Request request,
            String memberId
            ) {

        validateDeveloperLevel(
                request.getExperienceYears(),
                request.getDeveloperLevel()
        );

    }

    private void validateDeveloperLevel(Integer experienceYears, DeveloperLevel developerLevel) {
        if (developerLevel == DeveloperLevel.SENIOR
                && experienceYears < 10) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNGNIOR
                && (experienceYears < 4 || experienceYears > 10)) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }

        if (developerLevel == DeveloperLevel.JUNIOR && experienceYears > 4) {
            throw new DMakerException(LEVEL_EXPERIENCE_YEAR_NOT_MATCHED);
        }
    }
    //추후에 변화가능성을 열어뒀을때 Transactional 이용
    @Transactional
    public DeveloperDetatilDTO deleteDeveloper(String memberId) {
        //1. EMPLOYED ->RETIRED
        Developer developer = developerRepository.findByMemberId(memberId)
                .orElseThrow(()-> new DMakerException(NO_DEVELOPER));
        developer.setStatusCode(StatusCode.RETIRED);

        if(developer !=null) throw new DMakerException(NO_DEVELOPER);
        //2. save into RetiredDeveloper
        RetiredDeveloper retiredDeveloper = RetiredDeveloper.builder()
                .memberId(memberId)
                .name(developer.getName())
                .build();

        retiredDeveloperRepository.save(retiredDeveloper);
        return DeveloperDetatilDTO.fromEntity(developer);
    }
}
