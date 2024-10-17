package com.clubber.ClubberServer.util;

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
		// club 추가
		entityManager.createNativeQuery(
			"insert into club (id, name, club_type, hashtag, department, division, college, is_deleted, is_agree_to_provide_info, is_agree_to_review) "
				+ "values (10000000, '동아리 1', 'CENTER', 'ETC', 'ETC', 'ETC','ETC', false, true, true);").executeUpdate();

		// admin 추가
		entityManager.createNativeQuery(
				"insert into admin (id, username, password, account_state, account_role, club_id) VALUES (10000000, '동아리 1', ?, 'ACTIVE', 'ADMIN', 10000000)", Admin.class)
			.setParameter(1, encodedPassword) // 인코딩된 비밀번호를 쿼리에 설정
			.executeUpdate();

		//review 추가
		entityManager.createNativeQuery(
			"insert into review (id, club_id, content, approved_status, user_id) values (100000000, 10000000, '승인 대기 댓글', 'PENDING', 1000000)")
			.executeUpdate();

		//favorite 추가
		entityManager.createNativeQuery(
				"insert into favorite (id, club_id, is_deleted, user_id) values (100000000, 10000000, false, 1000000)")
			.executeUpdate();
	}


}
