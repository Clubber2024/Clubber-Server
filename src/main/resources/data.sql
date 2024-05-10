INSERT INTO Club_Info (room, total_view, activity, content, instagram, leader)
VALUES
    (101, 500, '학술 활동', '땅사입니다!.', 'http://ttangsa.com/clubexample', '홍길동'),
    (102, 1500, '봉사 활동', '봉사동아리 로타랙스입니다', 'http://rola.com/sportsclub', '김민준'),
    (103, 1200, '봉사 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://ssuleadership.com/cultureclub', '강예은'),
    (104, 500, '학술 활동', '숭실리더십학생협회입니다.', 'http://instagram.com/clubexample', '조성은'),
    (105, 1500, '스포츠 활동', '어리어리어리회입니당.', 'http://instagram.com/sportsclub', '권나래'),
    (106, 1200, '문화 활동', '호우회입니다.', 'http://instagram.com/cultureclub', '이영아'),
    (107, 500, '학술 활동', '소리보임입니다!.', 'http://sound.com/clubexample', '이지우'),
    (108, 1500, '종교 활동', 'ccc입니다.', 'http://instagram.com/sportsclub', '김권우'),
    (109, 1200, '종교 활동', 'ivf입니다.', 'http://instagram.com/cultureclub', '강지현'),
    (110, 1200, '종교 활동', '가톨릭학생회입니당.', 'http://instagram.com/cultureclub', '나현아'),
    (111, 1500, '종교 활동', 'joy선교회.', 'http://instagram.com/sportsclub', '김수영'),
    (112, 1200, '종교 활동', '네비케이토입니다.', 'http://instagram.com/cultureclub', '이영희'),
    (113, 1200, '창작 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (114, 1200, '창작 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '이영희'),
    (115, 1200, '창작 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '하하'),
    (165, 1200, '창작 활동', '문화 활동과 관련된 이벤트를 주최합니다.', 'http://instagram.com/cultureclub', '유');

INSERT INTO club (club_info_id, id, club_type, college, department, division, hashtag, name)
VALUES
    (1, 1, 'center', NULL, NULL, '연대사업분과', '취미', '땅의사람들'),
    (2, 2, 'center', NULL, NULL, '연대사업분과', '봉사', '로타랙트'),
    (3, 3, 'center', NULL, NULL, '연대사업분과', '취미', '숭실리더십학생협회'),
    (4, 4, 'center', NULL, NULL, '연대사업분과', '봉사', '어리회'),
    (5, 5, 'center', NULL, NULL, '연대사업분과', '봉사', '어린이 사랑회'),
    (6, 6, 'center', NULL, NULL, '연대사업분과', '봉사', '호우회'),
    (7, 7, 'center', NULL, NULL, '연대사업분과', '봉사', '소리보임'),
    (8, 8, 'center', NULL, NULL, '종교학과', '종교', 'CCC'),
    (9, 9, 'center', NULL, NULL, '종교학과', '종교', 'IVF'),
    (10, 10, 'center', NULL, NULL, '종교학과', '종교', '가톨릭학생회'),
    (11, 11, 'center', NULL, NULL, '종교학과', '종교', 'JOY선교회'),
    (12, 12, 'center', NULL, NULL, '종교학과', '종교', '네비게이토'),
    (13, 13, 'center', NULL, NULL, '창작전시분과', '전시', 'FRAME'),
    (14, 14, 'center', NULL, NULL, '창작전시분과', '전시', '멀티미디어연구회'),
    (15, 15, 'small', 'IT대학', '소프트웨어학부', NULL, '밴드', 'SUMMIT'),
    (16, 16, 'small', 'IT대학', '컴퓨터학부', NULL, '개발', 'ASC');