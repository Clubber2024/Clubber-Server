package com.clubber.domain.faq.controller;

import com.clubber.domain.faq.service.FaqService;
import com.clubber.global.config.swagger.DisableSwaggerSecurity;
import com.clubber.common.vo.enums.EnumMapperVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/faqs")
@Tag(name = "[FAQ]")
public class FaqController {

    private final FaqService faqService;

    @DisableSwaggerSecurity
    @GetMapping
    @Operation(summary = "전체 faq 목록 조회", description = "자주하는 질문 & 응답 반환")
    public List<EnumMapperVO> getTotalFaqs(){
        return faqService.getTotalFaqs();
    }
}
