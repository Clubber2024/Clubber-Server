package com.clubber.ClubberServer.domain.admin.facade;

import com.clubber.ClubberServer.domain.admin.dto.GetAdminPasswordFindRequest;
import com.clubber.ClubberServer.domain.admin.service.AdminAccountService;
import com.clubber.ClubberServer.global.infrastructure.outer.mail.MailService;
import com.clubber.ClubberServer.global.util.RandomAuthCodeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAccountFacade {
    private final MailService mailService;
    private final AdminAccountService adminAccountService;

    public void getAdminPasswordFind(GetAdminPasswordFindRequest getAdminPasswordFindRequest) {
        Integer authCode = RandomAuthCodeUtil.generateRandomInteger(6);
        String email = getAdminPasswordFindRequest.getEmail();

        mailService.send(email, "[클러버] 비밀번호 찾기 인증 번호입니다.", authCode.toString());
        adminAccountService.saveAdminPasswordFind(email, authCode);
    }
}
