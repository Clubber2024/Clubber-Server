package com.clubber.ClubberServer.integration.domain.favorite.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.clubber.ClubberServer.domain.user.service.UserService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import com.clubber.ClubberServer.integration.util.WithMockCustomUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class FavoriteServiceTest extends ServiceTest {

	@Autowired
	private UserService userService;

	@Test
	@WithMockCustomUser(second = "USER")
	@DisplayName("회원의 동아리 즐겨찾기 여부를 반환한다.")
	void getIsUserFavoriteTest() {
		//given - before Each에 수행

		//when
		boolean isUserFavorite = userService.getIsUserFavorite(1L);

		//then
		assertThat(isUserFavorite).isTrue();
	}
}
