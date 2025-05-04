package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.dto.*;
import com.clubber.ClubberServer.domain.admin.implement.AdminAppender;
import com.clubber.ClubberServer.domain.admin.implement.AdminReader;
import com.clubber.ClubberServer.domain.admin.implement.AdminValidator;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.admin.util.AdminUtil;
import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.global.event.signup.SignUpAlarmEventPublisher;
import com.clubber.ClubberServer.global.event.withdraw.SoftDeleteEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminAccountService {

    private final AdminReader adminReader;
    private final AdminEmailAuthService adminEmailAuthService;
    private final PendingAdminInfoRepository pendingAdminInfoRepository;
    private final AdminValidator adminValidator;
    private final PasswordEncoder passwordEncoder;
    private final SoftDeleteEventPublisher eventPublisher;
    private final SignUpAlarmEventPublisher signUpAlarmEventPublisher;
    private final AdminAppender adminAppender;

    @Transactional(readOnly = true)
    public GetAdminsProfileResponse getAdminsProfile() {
        Admin admin = adminReader.getCurrentAdmin();
        return GetAdminsProfileResponse.from(admin);
    }

    public UpdateAdminsPasswordResponse updateAdminsPassword(
            UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
        Admin admin = adminReader.getCurrentAdmin();

        String storedEncodedPassword = admin.getPassword();
        adminValidator.validatePasswordInUpdatePassword(updateAdminsPasswordRequest.getOldPassword(), storedEncodedPassword);

        String newPassword = updateAdminsPasswordRequest.getNewPassword();
        adminValidator.validateEqualsWithExistPassword(newPassword, storedEncodedPassword);

        adminAppender.updatePassword(admin, newPassword);
        return UpdateAdminsPasswordResponse.of(admin);
    }

    public UpdateAdminContactResponse updateAdminContact(UpdateAdminContactRequest updateAdminContactRequest) {
        Admin admin = adminReader.getCurrentAdmin();
        admin.updateContact(updateAdminContactRequest.getContact());
        return new UpdateAdminContactResponse(admin.getId(), admin.getContact());
    }

    public UpdateAdminEmailResponse updateAdminEmail(UpdateAdminEmailRequest updateAdminEmailRequest) {
        Admin admin = adminReader.getCurrentAdmin();
        Long adminId = admin.getId();

        adminEmailAuthService.checkAdminUpdateEmailAuthVerified(adminId, updateAdminEmailRequest.getAuthCode());
        adminEmailAuthService.deleteAdminUpdateEmailAuthById(adminId);
        admin.updateEmail(updateAdminEmailRequest.getEmail());
        return new UpdateAdminEmailResponse(admin.getId(), admin.getEmail());
    }

    public void withDraw() {
        Admin admin = adminReader.getCurrentAdmin();
        admin.withDraw();
        Club club = admin.getClub();
        club.delete();
        eventPublisher.throwSoftDeleteEvent(club.getId());
    }

    public CreateAdminSignUpResponse createAdminSignUp(CreateAdminSignUpRequest createAdminSignUpRequest) {
        String clubName = createAdminSignUpRequest.getClubName();
        adminEmailAuthService.checkAdminSignupAuthVerified(clubName, createAdminSignUpRequest.getAuthCode());
        adminEmailAuthService.deleteAdminSingupAuthById(clubName);

        String encodedPassword = passwordEncoder.encode(createAdminSignUpRequest.getPassword());
        PendingAdminInfo pendingAdminInfo = createAdminSignUpRequest.toEntity(encodedPassword);
        pendingAdminInfoRepository.save(pendingAdminInfo);

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
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        admin.updatePassword(encodedPassword);
    }
}
