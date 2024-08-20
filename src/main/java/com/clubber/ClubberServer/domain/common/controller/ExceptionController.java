package com.clubber.ClubberServer.domain.common.controller;


import com.clubber.ClubberServer.domain.common.service.ExceptionService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
@RequiredArgsConstructor
public class ExceptionController {

    private final ExceptionService exceptionService;

    @Operation(summary = "예외 메시지 전체 목록", description = "api 구분 없음")
    @GetMapping
    public List<String> getErrorReasons(){
        return exceptionService.getErrorReasons();
    }
}
