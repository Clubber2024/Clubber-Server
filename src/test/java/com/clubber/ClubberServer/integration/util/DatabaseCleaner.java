package com.clubber.ClubberServer.integration.util;

import static com.clubber.ClubberServer.integration.util.fixture.ReviewFixture.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.clubber.ClubberServer.domain.admin.domain.Admin;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Component
@ActiveProfiles("local")
public class DatabaseCleaner {

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private PasswordEncoder encoder;

	@Transactional
	public void insertInitialData() {

		String encodedPassword = encoder.encode("비밀번호 1");

		//clubinfo 추가
		entityManager.createNativeQuery(
			"insert into club_info (id, room, activity, instagram, leader) "
				+ "values (?, 1, 'activity', 'instagram', 'leader');")
			.setParameter(1, exampleId).executeUpdate();

		// club 추가
		entityManager.createNativeQuery(
			"insert into club (id, name, club_type, hashtag, department, division, college, is_deleted, is_agree_to_provide_info, is_agree_to_review, club_info_id, image_url) "
				+ "values (?, '동아리 1', 'CENTER', 'ETC', 'ETC', 'ETC','ETC', false, true, true, ?, '기존imageUrl');")
			.setParameter(1, exampleId)
			.setParameter(2, exampleId)
			.executeUpdate();

		//
		// entityManager.createNativeQuery(
		// 	"insert into club (id, name, club_type, hashtag, department, division, college, is_deleted, is_agree_to_provide_info, is_agree_to_review) "
		// 		+ "values (10000001, '동아리 1', 'CENTER', 'ETC', 'ETC', 'ETC','ETC', false, true, true);").executeUpdate();


		// admin 추가
		entityManager.createNativeQuery(
				"insert into admin (id, username, password, account_state, account_role, club_id) VALUES (?, '동아리 1', ?, 'ACTIVE', 'ADMIN', ?)", Admin.class)
			.setParameter(1, exampleId)
			.setParameter(2, encodedPassword) // 인코딩된 비밀번호를 쿼리에 설정
			.setParameter(3, exampleId)
			.executeUpdate();

		//user 추가
		entityManager.createNativeQuery(
				"insert into user(id, email, sns_type, sns_id, role, account_state) values (?, 'user@gmail.com', 'KAKAO', 1, 'USER', 'ACTIVE')"
			).setParameter(1, exampleId)
			.executeUpdate();

		//review 추가
		entityManager.createNativeQuery(
			"insert into review (id, club_id, content, approved_status, user_id) values (?, ?, '승인 대기 댓글', 'PENDING', ?)")
			.setParameter(1, exampleId)
			.setParameter(2, exampleId)
			.setParameter(3, exampleId)
			.executeUpdate();

		//favorite 추가
		entityManager.createNativeQuery(
				"insert into favorite (id, club_id, is_deleted, user_id) values (?, ?, false, ?)")
			.setParameter(1, exampleId)
			.setParameter(2, exampleId)
			.setParameter(3, exampleId)
			.executeUpdate();

		//recruit 추가
		entityManager.createNativeQuery(
				"insert into recruit(id, title, content, total_view, is_deleted, club_id) values (?, 'title', 'content', 1, false, ?)")
				.setParameter(1, exampleId)
				.setParameter(2, exampleId)
				.executeUpdate();
	}
}
