package com.clubber.ClubberServer.integration.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@Component
@ActiveProfiles("test")
public class DatabaseCleaner {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PasswordEncoder encoder;

    @Transactional
    public void insertInitialData() {

        String encodedPassword = encoder.encode("비밀번호 1");

        //clubinfo1 추가
        entityManager.createNativeQuery(
            "insert into club_info (id, room, activity, instagram, leader) "
                + "values (1, 100, 'activity', 'instagram', 'leader');"
        ).executeUpdate();

        // club1 추가
        entityManager.createNativeQuery(
            "insert into club (id, name, club_type, hashtag, department, division, college, is_deleted, is_agree_to_provide_info, is_agree_to_review, club_info_id, image_url) "
                + "values (1, '동아리 1', 'CENTER', 'ETC', 'ETC', 'ART','ETC', false, true, true, 1, '기존imageUrl');"
        ).executeUpdate();

        //clubinfo2 추가
        entityManager.createNativeQuery(
            "insert into club_info (id, room, activity, instagram, leader) "
                + "values (2, 100, 'activity', 'instagram', 'leader');"
        ).executeUpdate();

        // club2 추가
        entityManager.createNativeQuery(
            "insert into club (id, name, club_type, hashtag, department, division, college, is_deleted, is_agree_to_provide_info, is_agree_to_review, club_info_id, image_url) "
                + "values (2, '동아리 2', 'CENTER', 'ETC', 'ETC', 'EDUCATION','ETC', false, true, true, 2, '기존imageUrl');"
        ).executeUpdate();

        // admin 추가
        entityManager.createNativeQuery(
                "insert into admin (id, username, password, account_state, account_role, club_id)"
                    + "VALUES (1, '동아리 1', ?, 'ACTIVE', 'ADMIN', 1)")
            .setParameter(1, encodedPassword) // 인코딩된 비밀번호를 쿼리에 설정
            .executeUpdate();

        //user1 추가
        entityManager.createNativeQuery(
            "insert into user(id, email, sns_type, sns_id, role, account_state) "
                + "values (1, 'user@gmail.com', 'KAKAO', 1, 'USER', 'ACTIVE')"
        ).executeUpdate();

        //user2 추가
        entityManager.createNativeQuery(
            "insert into user(id, email, sns_type, sns_id, role, account_state) "
                + "values (2, 'user2@gmail.com', 'KAKAO', 2, 'USER', 'ACTIVE')"
        ).executeUpdate();

        //review1 추가 (user1 -> club1)
        entityManager.createNativeQuery(
            "insert into review (id, club_id, content, approved_status, user_id, verified_status) "
                + "values (1, 1, '승인 대기 댓글', 'PENDING', 1, 'NOT_VERIFIED')"
        ).executeUpdate();

        //review2 추가 (user2 -> club1)
        entityManager.createNativeQuery(
            "insert into review (id, club_id, content, approved_status, user_id, verified_status) "
                + "values (2, 1, '승인 대기 댓글', 'PENDING', 2, 'NOT_VERIFIED')"
        ).executeUpdate();

        //favorite 추가 (user1 -> club1)
        entityManager.createNativeQuery(
            "insert into favorite (id, club_id, is_deleted, user_id) "
                + "values (1, 1, false, 1)"
        ).executeUpdate();

        //recruit1 추가
        entityManager.createNativeQuery(
            "insert into recruit(id, title, content, total_view, is_deleted, club_id) "
                + "values (1, 'title', 'content', 100, false, 1)"
        ).executeUpdate();

        //recruit2 추가
        entityManager.createNativeQuery(
            "insert into recruit(id, title, content, total_view, is_deleted, club_id) "
                + "values (2, 'title', 'content', 100, false, 1)"
        ).executeUpdate();

        entityManager.createNativeQuery(
            "insert into recruit_image(id, image_url, is_deleted, order_num, recruit_id) "
                + "values (1, 'image1',  false, 1, 1)"
        ).executeUpdate();

        entityManager.createNativeQuery(
            "insert into recruit_image(id, image_url, is_deleted, order_num, recruit_id)"
                + " values (2, 'image2',  false, 2, 1)"
        ).executeUpdate();
    }
}
