package com.clubber.ClubberServer.domain.faq.service;

import com.clubber.ClubberServer.global.mapper.enums.EnumFaqMapper;
import com.clubber.ClubberServer.global.vo.enums.EnumFaQMapperVO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FaqService {

	private final EnumFaqMapper enumFaqMapper;

	public List<EnumFaQMapperVO> getTotalFaqs() {
		return enumFaqMapper.get("FaQ");
	}
}
