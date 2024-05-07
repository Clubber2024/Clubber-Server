INSERT INTO Club_Info (room, total_view, activity, content, instagram, leader)
VALUES
    (101, 500, '학술 활동', '동아리에 대한 자세한 소개입니다.', 'http://instagram.com/clubexample', '홍길동'),
    (102, 1500, '스포츠 활동', '다양한 스포츠 활동을 지원하는 동아리입니다.', 'http://instagram.com/sportsclub', '김철수'),
    (103, 1200, '문화 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (104, 500, '학술 활동', '동아리에 대한 자세한 소개입니다.', 'http://instagram.com/clubexample', '홍길동'),
    (105, 1500, '스포츠 활동', '다양한 스포츠 활동을 지원하는 동아리입니다.', 'http://instagram.com/sportsclub', '김철수'),
    (106, 1200, '문화 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (107, 500, '학술 활동', '동아리에 대한 자세한 소개입니다.', 'http://instagram.com/clubexample', '홍길동'),
    (108, 1500, '스포츠 활동', '다양한 스포츠 활동을 지원하는 동아리입니다.', 'http://instagram.com/sportsclub', '김철수'),
    (110, 1200, '문화 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (111, 1200, '문화 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (112, 1200, '문화 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희');

INSERT INTO club (club_info_id, id, club_type, college, department, division, hashtag, name)
VALUES
    (1, 1, 'center', NULL, NULL, '연대사업분과', '취미', '땅의사람들'),
    (2, 2, 'center', NULL, NULL, '연대사업분과', '봉사', '로타랙트'),
    (3, 3, 'center', NULL, NULL, '연대사업분과', '취미', '숭실리더십학생협회'),
    (4, 4, 'center', NULL, NULL, '연대사업분과', '봉사', '어리회'),
    (5, 5, 'center', NULL, NULL, '연대사업분과', '봉사', '어린이 사랑회'),
    (6, 6, 'center', NULL, NULL, '연대사업분과', '봉사', '호우회'),
    (7, 7, 'small', 'IT대학', 'IT대학', NULL, '밴드', 'SUMMIT'),
    (8, 8, 'small', 'IT대학', '컴퓨터학부', NULL, '개발', 'SCCC'),
    (9, 9, 'center', NULL, NULL, '교양분과', '취미', 'SRC'),
    (10, 10, 'center', NULL, NULL, '교양분과', '패션', 'ssulook'),
    (11, 11, 'center', NULL, NULL, '종교학과', '종교', 'CCC');