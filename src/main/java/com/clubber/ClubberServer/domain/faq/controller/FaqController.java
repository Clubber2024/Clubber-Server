package com.clubber.ClubberServer.domain.faq.controller;

import com.clubber.ClubberServer.domain.faq.dto.GetFaqsResponse;
import com.clubber.ClubberServer.domain.faq.service.FaqService;
import com.clubber.ClubberServer.global.config.swagger.DisableSwaggerSecurity;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/faqs")
public class FaqController {

    private final FaqService faqService;

    @DisableSwaggerSecurity
    @GetMapping
    public List<GetFaqsResponse> getTotalFaqs(){
        return faqService.getTotalFaqs();
    }
}
