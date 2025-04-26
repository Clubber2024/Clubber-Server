package com.clubber.ClubberServer.domain.faq.service;

import com.clubber.ClubberServer.global.mapper.enums.EnumMapper;
import com.clubber.ClubberServer.global.mapper.enums.EnumMapperVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FaqService {

	private final EnumMapper enumMapper;

	public List<EnumMapperVO> getTotalFaqs() {
		return enumMapper.get("FaQ");
	}
}
