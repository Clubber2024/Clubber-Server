package com.clubber.ClubberServer.domain.faq.domain;

import com.clubber.ClubberServer.global.enummapper.EnumMapperType;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Faq implements EnumMapperType {
	QUESTION_1("조회하고 싶은 동아리를 볼 수 없어요. 어떻게 볼 수 있나요 ?", "정보제공 동의가 안된 동아리의 경우, 동아리 정보가 보이지 않습니다.  정보 제공 원하는 동아리를 따로 요청 해주시면 해당 동아리와 상의 후 추가하겠습니다"),
	QUESTION_2("제가 작성한 동아리 리뷰 한줄평이 동아리 페이지에 보이지 않습니다.", "작성된 한줄평 리뷰는 무분별한 비방댓글을 막기 위해 동아리 계정의 승인이 된 경우에 보일 수 있습니다."),
	QUESTION_3("실제 동아리 회원이었는지는 어떻게 인증하나요?", "동아리 회원 명부를 제공 받기 힘든 상황이기 때문에 실제 동아리 회원 여부는 인증하기 힘들다는 점 양해 부탁드립니다."),
	QUESTION_4("제가 작성한 리뷰 수정 및 삭제가 가능한가요?", "답변4"),
	QUESTION_5("제가 즐겨찾기 한 동아리는 어디서 확인할 수 있나요?", "로그인 후 마이페이지를 클릭해 나의 즐겨찾기로 들어가시면 한눈에 확인하실 수 있습니다."),
	QUESTION_6("리뷰 쓴 작성자가 누군지 알 수 있나요?", "리뷰 작성은 익명으로 처리되어 리뷰 작성자 본인 이외에 확인이 불가능합니다. "),
	QUESTION_7("이전에 승인 거절한 리뷰는 다시 열람 가능한가요?", "관리자 마이페이지의 리뷰 목록에서 승인 여부를 포함한 모든 리뷰 열람이 가능합니다."),
	QUESTION_8("동아리 등록 절차은 어떻게 되나요?", "하단에 있는 클러버 이메일 또는 인스타를 통해 연락주시면 등록이 가능합니다. ");
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

	private String title;
	private String answer;
}
