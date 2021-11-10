package com.fastcampus.programming.dmaker.controller;

import com.fastcampus.programming.dmaker.repository.DeveloperRepository;
import com.fastcampus.programming.dmaker.service.DMakerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<String> getAllDevelopers(){
        // GET /developers HTTP/1.1
        log.info("GET /developers HTTP/1.1");

        return Arrays.asList("Snow","Elsa","Olaf");
    }

    @GetMapping("/create-developer")
    public List<String> createDevelopers(){
        // GET /developers HTTP/1.1
        log.info("GET /create-developer HTTP/1.1");

        dMakerService.createDeveloper();
        return List.of("Olaf");
    }
}
