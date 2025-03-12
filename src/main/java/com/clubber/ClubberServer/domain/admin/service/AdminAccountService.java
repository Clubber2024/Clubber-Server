package com.clubber.ClubberServer.domain.admin.service;

import com.clubber.ClubberServer.domain.admin.domain.Admin;
import com.clubber.ClubberServer.domain.admin.domain.PendingAdminInfo;
import com.clubber.ClubberServer.domain.admin.dto.*;
import com.clubber.ClubberServer.domain.admin.repository.AdminRepository;
import com.clubber.ClubberServer.domain.admin.repository.PendingAdminInfoRepository;
import com.clubber.ClubberServer.domain.admin.validator.AdminValidator;
import com.clubber.ClubberServer.domain.user.domain.AccountState;
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

    private final AdminReadService adminReadService;
    private final AdminRepository adminRepository;
    private final AdminEmailAuthService adminEmailAuthService;
    private final PendingAdminInfoRepository pendingAdminInfoRepository;
    private final AdminValidator adminValidator;
    private final PasswordEncoder passwordEncoder;
    private final SoftDeleteEventPublisher eventPublisher;
    private final SignUpAlarmEventPublisher signUpAlarmEventPublisher;

    @Transactional(readOnly = true)
    public GetAdminsProfileResponse getAdminsProfile() {
        Admin admin = adminReadService.getCurrentAdmin();
        return GetAdminsProfileResponse.from(admin);
    }

    public UpdateAdminsPasswordResponse updateAdminsPassword(
            UpdateAdminsPasswordRequest updateAdminsPasswordRequest) {
        Admin admin = adminReadService.getCurrentAdmin();
        String storedEncodedPassword = admin.getPassword();
        adminValidator.validatePasswordInUpdatePassword(updateAdminsPasswordRequest.getOldPassword(), storedEncodedPassword);

        String newPassword = updateAdminsPasswordRequest.getNewPassword();
        adminValidator.validateEqualsWithExistPassword(newPassword, storedEncodedPassword);

        admin.updatePassword(passwordEncoder.encode(newPassword));
        return UpdateAdminsPasswordResponse.of(admin);
    }

    public void withDraw() {
        Admin admin = adminReadService.getCurrentAdmin();
        admin.withDraw();
        eventPublisher.throwSoftDeleteEvent(admin.getId());
    }

    public CreateAdminSignUpResponse createAdminSignUp(
            CreateAdminSignUpRequest createAdminSignUpRequest) {
        String encodedPassword = passwordEncoder.encode(createAdminSignUpRequest.getPassword());
        PendingAdminInfo pendingAdminInfo = createAdminSignUpRequest.toEntity(encodedPassword);
        pendingAdminInfoRepository.save(pendingAdminInfo);
        signUpAlarmEventPublisher.throwSignUpAlarmEvent(pendingAdminInfo.getClubName(), pendingAdminInfo.getContact());
        return CreateAdminSignUpResponse.from(pendingAdminInfo);
    }

    @Transactional(readOnly = true)
    public GetAdminUsernameCheckDuplicateResponse getAdminUsernameCheckDuplicate(String username){
        boolean isExist = adminRepository.existsByUsernameAndAccountState(username, AccountState.ACTIVE);
        return new GetAdminUsernameCheckDuplicateResponse(username, !isExist);
    }

    @Transactional(readOnly = true)
    public GetAdminUsernameFindResponse getAdminUsernameFind(GetAdminUsernameFindRequest request) {
        adminEmailAuthService.validateAdminUsernameFindAuth(request.getClubId(), request.getAuthCode());
        Admin admin = adminReadService.getAdminByEmailAndClubId(request.getEmail(), request.getClubId());
        return new GetAdminUsernameFindResponse(admin.getUsername());
    }
}
