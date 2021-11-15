package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.dto.CreateDeveloper;
import com.fastcampus.programming.dmaker.dto.DeveloperDTO;
import com.fastcampus.programming.dmaker.dto.DeveloperDetatilDTO;
import com.fastcampus.programming.dmaker.dto.UpdateDeveloper;
import com.fastcampus.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
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
    @GetMapping("/developers")
    public List<DeveloperDTO> getAllDevelopers() {
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return dMakerService.getAllDevelopers();
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
    @PostMapping("/create-developer")
    public CreateDeveloper.Response createDevelopers(
            //request요청을받기위해사용
            @Valid @RequestBody CreateDeveloper.Request request
    ) {
        // GET /developers HTTP/1.1
        log.info("request:{]", request);

        return dMakerService.createDeveloper(request);

    }
}
