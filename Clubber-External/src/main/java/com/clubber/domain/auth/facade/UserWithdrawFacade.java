package com.clubber.domain.auth.facade;

import com.clubber.global.infrastructure.outer.api.oauth.kakao.dto.UnlinkKaKaoTarget;
import com.clubber.domain.auth.service.AuthService;
import com.clubber.domain.domains.user.domain.User;
import com.clubber.domain.user.implement.UserReader;
import com.clubber.global.infrastructure.outer.api.oauth.kakao.client.KakaoInfoClient;
import com.clubber.global.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWithdrawFacade {

	private final KakaoInfoClient kakaoInfoClient;
	private final KakaoProperties kakaoProperties;
	private final UserReader userReader;
	private final AuthService authService;

	public void withDraw() {
		User user = userReader.getCurrentUser();
		unlinkKakao(user);
		authService.deleteKakaoUser(user);
	}

	private void unlinkKakao(User user) {
		String header = "KakaoAK " + kakaoProperties.getAdminKey();
		UnlinkKaKaoTarget unlinkKakaoTarget = UnlinkKaKaoTarget.from(user.getSnsId());
		kakaoInfoClient.unlink(header, unlinkKakaoTarget);
	}
}
