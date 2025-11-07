package com.clubber.domain.faq.service;

import com.clubber.common.mapper.enums.EnumMapper;
import com.clubber.common.vo.enums.EnumMapperVO;
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
