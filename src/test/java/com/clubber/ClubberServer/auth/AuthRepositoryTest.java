package com.clubber.ClubberServer.auth;


import com.clubber.ClubberServer.domain.user.repository.UserRepository;
import com.clubber.ClubberServer.global.config.security.SecurityConfig;
import com.clubber.ClubberServer.global.error.BaseErrorCode;
import com.clubber.ClubberServer.global.error.ErrorReason;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.dto.KakaoUserInfoResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Import({SecurityConfig.class})
public class AuthRepositoryTest {

    @Autowired
    private UserRepository userRepository;

//    @Autowired
//    private PasswordEncoder encoder;

//    @Test
//    void signupTest() {
//        //given
//        User user = User.builder()
//                .snsId(1L)
//                .email("email")
//                .snsType(SnsType.KAKAO)
//                .build();
//        //when
//        User savedUser = userRepository.save(user);
//
//        //then
//        //assertEquals(savedUser.getUserStatus(), UserStatus.ACTIVE);
//        //assertEquals(savedUser.getId(), 5000003);
//        //assertEquals(savedUser.getRole(), AccountRole.USER);
//        assertEquals(savedUser.getSnsType(), SnsType.KAKAO);
//    }
//
//    @Test
//    void passwordtest() {
//        //given
//        String p1 = encoder.encode("123");
//        String p2 = encoder.encode("123");
//        assertEquals(p1,p2);
//    }
//
//    @Test
//    void passwordtest2(){
//        String p1 = "123";
//        String p2 = encoder.encode("123");
//        System.out.println("p2 = " + p2);
//        assertEquals(true, encoder.matches(p1,p2));
//    }



    @Test
    void reflectiontest() {
        Set<Class<? extends BaseErrorCode>> errorCodes =
                new Reflections("com.clubber").getSubTypesOf(BaseErrorCode.class);
        System.out.println("errorCodes = " + errorCodes);
        // Enum 상수를 가져와서 reasons 리스트에 담습니다.
        List<String> reasons = errorCodes.stream()
                .flatMap(enumClass -> {
                    BaseErrorCode[] enumConstants = enumClass.getEnumConstants();
                    System.out.println("enumConstants = " + enumConstants);
                    return Arrays.stream(enumConstants)
                            .map(BaseErrorCode::getErrorReason)
                            .map(ErrorReason::getReason);
                })
                .collect(Collectors.toList());

        // 결과 출력
        System.out.println("으아");
        System.out.println("reasons = " + reasons);


    }


}
