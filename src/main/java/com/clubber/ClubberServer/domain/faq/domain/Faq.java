package com.clubber.ClubberServer.domain.faq.domain;

import com.clubber.ClubberServer.global.mapper.enums.EnumMapperType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Faq implements EnumMapperType {
	QUESTION_1("클러버는 어떤 서비스인가요?", "클러버는 숭실대 학생들의 동아리 동아리 활동을 돕기 위해 IT 대학 학생들이 자체적으로 만든 서비스에요."),
	QUESTION_2("숭실대학교 동아리 연합회와 관련이 있는 건가요?.", "동아리 연합회와는 관련이 없는 독립적인 서비스에요."),
	QUESTION_3("제가 즐겨찾기 한 동아리는 어디서 확인할 수 있나요?", "로그인 후 [마이페이지 - 나의 즐겨찾기]에서 확인하실 수 있어요."),
	QUESTION_4("해시태그 분류 기준은 무엇인가요?",
		"자신에게 맞는 동아리 선택에 도움이 되기 위해 클러버에서 자체적으로 만든 기준이에요. 혹시 자신의 동아리가 다른 해시태그로의 분류를 희망한다면 동아리 계정 인스타로 연락 부탁드려요."),
	QUESTION_5("동아리 정보를 수정하고 싶어요", "혹시나 동아리 정보 수정이나 게시 중단을 원하시면 동아리 계정 인스타로 연락 부탁드려요."),
	QUESTION_6("추후 서비스 진행 계획이 어떻게 되나요?", "희망하는 동아리 운영진들 분들에게 동아리 정보 관리, 모집글 작성, 리뷰 기능을 준비하고 있어요.");

	private final String title;
	private final String answer;

	@Override
	public String getCode() {
		return name();
	}

	@Override
	public String getTitle() {
		return title;
	}

	public String getAnswer() {
		return answer;
	}
}
