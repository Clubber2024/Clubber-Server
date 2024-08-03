package com.clubber.ClubberServer.domain.faq.service;

import com.clubber.ClubberServer.domain.faq.domain.Faq;
import com.clubber.ClubberServer.domain.faq.dto.GetFaqsResponse;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqService {

    public List<GetFaqsResponse> getTotalFaqs(){
        return Arrays.stream(Faq.values())
                .map(GetFaqsResponse::from)
                .collect(Collectors.toList());
    }
}
