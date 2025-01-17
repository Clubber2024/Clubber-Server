package com.clubber.ClubberServer.domain.auth.component;

import com.clubber.ClubberServer.domain.auth.dto.UnlinkKaKaoTarget;
import com.clubber.ClubberServer.domain.auth.service.AuthService;
import com.clubber.ClubberServer.domain.user.domain.User;
import com.clubber.ClubberServer.domain.user.service.UserReadService;
import com.clubber.ClubberServer.global.infrastructure.outer.api.oauth.client.kakao.KakaoInfoClient;
import com.clubber.ClubberServer.global.properties.KakaoProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserWithdraw {

	private final KakaoInfoClient kakaoInfoClient;
	private final KakaoProperties kakaoProperties;
	private final UserReadService userReadService;
	private final AuthService authService;

	public void withDraw() {
		User user = userReadService.getUser();
		unlinkKakao(user);
		authService.deleteKakaoUser(user);
	}

	private void unlinkKakao(User user) {
		String header = "KakaoAK " + kakaoProperties.getAdminKey();
		UnlinkKaKaoTarget unlinkKakaoTarget = UnlinkKaKaoTarget.from(user.getSnsId());
		kakaoInfoClient.unlink(header, unlinkKakaoTarget);
	}
}
