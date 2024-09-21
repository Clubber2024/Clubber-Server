INSERT INTO Club_Info (room, total_view, activity, instagram, leader)
VALUES
    (101, 50, '개발 활동', 'http://ttangsa.com/clubexample', '홍길동'),
    (102, 43, '봉사 활동', 'http://rola.com/sportsclub', '김민준'),
    (103, 12, '봉사 활동', 'http://ssuleadership.com/cultureclub', '강예은'),
    (104, 11, '학술 활동', 'http://instagram.com/clubexample', '조성은'),
    (105, 1, '스포츠 활동', 'http://instagram.com/sportsclub', '권나래'),
    (106, 0, '문화 활동', 'http://hou.com/cultureclub', '이영아'),
    (107, 73, '학술 활동', 'http://soundboim.com/clubexample', '이지우'),
    (108, 21, '종교 활동', 'http://instagram.com/sportsclub', '김권우'),
    (109, 12, '종교 활동', 'http://instagram.com/cultureclub', '강지현'),
    (110, 11, '종교 활동', 'http://instagram.com/cultureclub', '나현아'),
    (111, 19, '밴드 활동','http://summit.com', '김수영'),
    (112, 7, '개발 활동', 'http://ASC', '정준하'),
    (113, 5, '개발 활동', 'http://UMC', '이기우'),
    (114, 30, '개발 활동', 'http://SCCC', '유재석');

INSERT INTO club (club_info_id, club_type,college,department, division, hashtag, image_url,introduction,name)
VALUES
    (1,  'center', NULL,NULL, '연대사업분과', '취미', NULL,'땅사입니다!.','땅의사람들'),
    (2,  'center', NULL,NULL, '연대사업분과', '봉사', NULL, '봉사동아리 로타랙스입니다','로타랙트'),
    (3,  'small', NULL, NULL, '연대사업분과', '개발', NULL,'문화 활동과 관련된 이벤트를 주최합니다.','숭실리더십학생협회'),
    (4,  'center', NULL, NULL, '연대사업분과', '봉사', NULL,'숭실리더십학생협회입니다.','어리회'),
    (5,  'center', NULL ,NULL, '연대사업분과', '봉사', NULL,'어리어리어리회입니당.','어린이 사랑회'),
    (6, 'center', NULL, NULL, '연대사업분과', '봉사',NULL,'호우회입니다.', '호우회'),
    (7,  'center', NULL, NULL, '연대사업분과', '봉사', NULL,'소리보임입니다','소리보임'),
    (8,  'center', NULL,NULL, '종교학과', '개발', NULL, 'ccc입니다.','CCC'),
    (9, 'center', NULL, NULL, '종교학과', '종교', NULL,'IVF입니다.','IVF'),
    (10, 'center', NULL, NULL, '종교학과', '종교', NULL,'가톨릭학생회입니다.','가톨릭학생회'),
    (11,  'small', 'IT대학','소프트웨어학부', NULL, '밴드', NULL, 'IT대학 소속 밴드부입니다.','SUMMIT'),
    (12,  'small', 'IT대학','소프트웨어학부', NULL, '개발', NULL, '보안동아리입니다.','ASC'),
    (13,  'center', 'IT대학','소프트웨어학부', NULL, '개발', NULL, '연합개발동아리입니다.','UMC'),
    (14,  'small', 'IT대학', '컴퓨터학부', NULL, '개발', NULL,'알고리즘 동아리입니다.','SCCC');

-- -- Insert reviews
-- INSERT INTO review (id, club_id, user_id)
-- VALUES (1, 1, 1),
--        (2, 2, 1);
--
-- -- Insert review_keywords
-- INSERT INTO review_keyword (id, review_id, keyword)
-- VALUES (1, 1, 'FEE'),
--        (2, 1, 'CULTURE'),
--        (3, 2, 'MANAGE'),
--        (4, 2, 'FEE');
