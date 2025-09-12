package com.clubber.domain.admin.service;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.domain.PendingAdminInfo;
import com.clubber.domain.admin.dto.*;
import com.clubber.domain.admin.implement.AdminAppender;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.admin.implement.AdminValidator;
import com.clubber.domain.admin.implement.PendingAdminInfoManager;
import com.clubber.domain.admin.util.AdminUtil;
import com.clubber.global.event.signup.SignUpAlarmEventPublisher;
import com.clubber.global.event.withdraw.SoftDeleteEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAccountService {

    private final AdminReader adminReader;
    private final AdminAppender adminAppender;
    private final AdminValidator adminValidator;
    private final AdminEmailAuthService adminEmailAuthService;
    private final SignUpAlarmEventPublisher signUpAlarmEventPublisher;
    private final SoftDeleteEventPublisher eventPublisher;
    private final PendingAdminInfoManager pendingAdminInfoManager;

    @Transactional(readOnly = true)
    public GetAdminsProfileResponse getAdminsProfile() {
        Admin admin = adminReader.getCurrentAdmin();
        return GetAdminsProfileResponse.from(admin);
    }

    public UpdateAdminsPasswordResponse updateAdminsPassword(
            UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
        Admin admin = adminReader.getCurrentAdmin();

        adminValidator.validateExistPassword(updateAdminsPasswordRequest.getOldPassword(), admin.getPassword());

        String newPassword = updateAdminsPasswordRequest.getNewPassword();
        adminValidator.validateEqualsWithExistPassword(newPassword, admin.getPassword());

        adminAppender.updatePassword(admin, newPassword);
        return UpdateAdminsPasswordResponse.of(admin);
    }

    public UpdateAdminContactResponse updateAdminContact(UpdateAdminContactRequest updateAdminContactRequest) {
        Admin admin = adminReader.getCurrentAdmin();
        adminAppender.updateContact(admin, updateAdminContactRequest.getContact());
        return new UpdateAdminContactResponse(admin.getId(), admin.getContact());
    }

    public UpdateAdminEmailResponse updateAdminEmail(UpdateAdminEmailRequest updateAdminEmailRequest) {
        Admin admin = adminReader.getCurrentAdmin();
        Long adminId = admin.getId();

        adminEmailAuthService.checkAdminUpdateEmailAuthVerified(adminId, updateAdminEmailRequest.getAuthCode());
        adminEmailAuthService.deleteAdminUpdateEmailAuthById(adminId);

        adminAppender.updateEmail(admin, updateAdminEmailRequest.getEmail());
        return new UpdateAdminEmailResponse(admin.getId(), admin.getEmail());
    }

    public void withDraw() {
        Admin admin = adminReader.getCurrentAdmin();
        Long clubId = adminAppender.withDraw(admin);
        eventPublisher.throwSoftDeleteEvent(clubId);
    }

    public CreateAdminSignUpResponse createAdminSignUp(CreateAdminSignUpRequest createAdminSignUpRequest) {
        String clubName = createAdminSignUpRequest.getClubName();
        adminEmailAuthService.checkAdminSignupAuthVerified(clubName, createAdminSignUpRequest.getAuthCode());
        adminEmailAuthService.deleteAdminSingupAuthById(clubName);

        PendingAdminInfo pendingAdminInfo = pendingAdminInfoManager.appendPendingAdminInfo(createAdminSignUpRequest);

        signUpAlarmEventPublisher.throwSignUpAlarmEvent(pendingAdminInfo.getClubName(), pendingAdminInfo.getContact());
        return CreateAdminSignUpResponse.from(pendingAdminInfo);
    }

    @Transactional(readOnly = true)
    public GetAdminUsernameCheckDuplicateResponse getAdminUsernameCheckDuplicate(String username) {
        boolean isExist = adminReader.existsByUsername(username);
        return new GetAdminUsernameCheckDuplicateResponse(username, !isExist);
    }

    @Transactional(readOnly = true)
    public GetAdminUsernameFindResponse getAdminUsernameFind(GetAdminUsernameFindRequest request) {
        Long clubId = request.getClubId();
        Integer authCode = request.getAuthCode();

        adminEmailAuthService.checkAdminUsernameFindAuthVerified(clubId, authCode);
        adminEmailAuthService.deleteAdminUsernameFindAuthById(clubId);

        Admin admin = adminReader.getAdminByEmailAndClubId(request.getEmail(), clubId);
        String maskedUsername = AdminUtil.maskUsername(admin.getUsername());

        return new GetAdminUsernameFindResponse(maskedUsername);
    }

    public void updateAdminResetPassword(UpdateAdminResetPasswordRequest request) {
        String username = request.getUsername();

        adminEmailAuthService.checkAdminPasswordFindAuthVerified(username, request.getAuthCode());
        adminEmailAuthService.deleteAdminPasswordFindAuthById(username);

        Admin admin = adminReader.getAdminByUsername(username);
        adminAppender.updatePassword(admin, request.getPassword());
    }
}
