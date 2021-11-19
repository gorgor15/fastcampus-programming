package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.*;
import com.fastcampus.programming.dmaker.exception.DMakerException;
import com.fastcampus.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * @author snow
 */
// 요청을 받았을때 실행
@Slf4j
@RestController
@RequiredArgsConstructor
public class DMakerController {
    private final DMakerService dMakerService;

    //              DMakerController(Bean)    DMakerService(Bean)   DeveloperRepository(Bean)
//    ===============================Spring Application Context=================================
    
    //전체보기
    @GetMapping("/developers")
    public List<DeveloperDTO> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllEmployedDevelopers();
    }

    //상세정보 가져오기
    @GetMapping("/developers/{memberId}")
    public DeveloperDetatilDTO getAllDeveloperDetail(
            @PathVariable String memberId
    ) {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getDeveloperDetail(memberId);
    }

    //수정하기
    @PutMapping("/developers/{memberId}")
    public DeveloperDetatilDTO UpdateDevelopers(
            @PathVariable String memberId,
            @Valid @RequestBody UpdateDeveloper.Request request
    ){
        log.info("GET /developers HTTP/1.1");

       return dMakerService.UpdateDeveloper(memberId,request);
    }
    @DeleteMapping("/developers/{memberId}")
    public DeveloperDetatilDTO deleteDevelopers(
            @PathVariable String memberId
    ){
        return dMakerService.deleteDeveloper(memberId);
    }



    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            //request요청을받기위해사용
            @Valid @RequestBody CreateDeveloper.Request request
    ) {
        // GET /developers HTTP/1.1
        log.info("request:{]", request);

        return dMakerService.createDeveloper(request);

    }
    
    //글로벌 예외처리문
    @ResponseStatus(value= HttpStatus.CONFLICT)
    @ExceptionHandler(DMakerException.class)
    public DMakerErrorResponse handleException(DMakerException e, HttpServletRequest request){
        log.error("errorCode:{}, url: {}, message:{}",e.getDMakerErrorCode(),request.getRequestURL(),e.getDetailMessage());

        return DMakerErrorResponse.builder()
                .errorCode(e.getDMakerErrorCode())
                .errorMessage(e.getDetailMessage())
                .build();
    }
}
