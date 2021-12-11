package com.fastcampus.programming.dmaker.service;

import com.fastcampus.programming.dmaker.code.StatusCode;
import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDTO;
import com.fastcampus.programming.dmaker.dto.DeveloperDetatilDTO;
import com.fastcampus.programming.dmaker.entity.Developer;
import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.repository.RetiredDeveloperRepository;
import com.fastcampus.programming.dmaker.type.DeveloperLevel;
import com.fastcampus.programming.dmaker.type.DeveloperSkillType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class DMakerServiceTest {

    @Mock
    private DeveloperRepository developerRepository;

    @Mock
    private RetiredDeveloperRepository retiredDeveloperRepository;

    @InjectMocks
    private DMakerService dMakerService;
    @Test
    public void testSomething(){
//        dMakerService.createDeveloper(CreateDeveloper.Request.builder()
//                        .developerLevel(DeveloperLevel.JUNIOR)
//                        .developerSkillType(DeveloperSkillType.BACK_END)
//                        .experienceYears(1)
//                        .memberId("memberId")
//                        .name("name")
//                        .age(32)
//                        .build());
//        List<DeveloperDTO> allEmployedDevelopers = dMakerService.getAllEmployedDevelopers();
//        System.out.println("============================");
//        System.out.println(allEmployedDevelopers);
//        System.out.println("============================");
        //junit에 검증방법 중하나
//        assertEquals("HelloWorld",result);
        
        
        //mockito 검증방법
        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.of(Developer.builder()
                        .developerLevel(DeveloperLevel.JUNIOR)
                        .developerSkillType(DeveloperSkillType.BACK_END)
                        .experienceYears(1)
                        .statusCode(StatusCode.EMPLOYED)
                        .name("name")
                        .age(12)
                        .build()));
        DeveloperDetatilDTO developerDetatilDTO = dMakerService.getDeveloperDetail("memberId");
        assertEquals(DeveloperLevel.JUNIOR,developerDetatilDTO.getDeveloperLevel());
        assertEquals(DeveloperSkillType.BACK_END,developerDetatilDTO.getDeveloperSkillType());
        assertEquals(1,developerDetatilDTO.getExperienceYears());
    }
    @Test
    void createDeveloperTest_success(){
        //given (moking 테스트에 활용될 지역변수)
        CreateDeveloper.Request request = CreateDeveloper.Request.builder()
                .developerLevel(DeveloperLevel.JUNIOR)
                .developerSkillType(DeveloperSkillType.BACK_END)
                .experienceYears(1)
                .memberId("memberId")
                .name("name")
                .age(12)
                .build();

        given(developerRepository.findByMemberId(anyString()))
                .willReturn(Optional.empty());
        ArgumentCaptor<Developer> captor =
                ArgumentCaptor.forClass(Developer.class);

        //when (테스트하는동작 , 결과값 받아오는것)
        CreateDeveloper.Response developer =  dMakerService.createDeveloper(request);
        //then (assertion 우리가 예상한 동작대로 동작하는지)
        verify(developerRepository,times(1))
                .save(captor.capture());
        Developer saveDeveloper = captor.getValue();
        assertEquals(DeveloperLevel.SENIOR,saveDeveloper.getDeveloperLevel());
        assertEquals(DeveloperSkillType.FRONT_END,saveDeveloper.getDeveloperSkillType());
        assertEquals(12,saveDeveloper.getExperienceYears());
    }



}