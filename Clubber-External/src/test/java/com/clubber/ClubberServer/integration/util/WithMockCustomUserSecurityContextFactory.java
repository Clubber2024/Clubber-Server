package com.clubber.ClubberServer.integration.util;

import com.clubber.global.config.security.AuthDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

public class WithMockCustomUserSecurityContextFactory implements
	WithSecurityContextFactory<WithMockCustomUser> {

	@Override
	public SecurityContext createSecurityContext(WithMockCustomUser customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		AuthDetails adminDetails = new AuthDetails(customUser.first(), customUser.second());
		UsernamePasswordAuthenticationToken adminToken = new UsernamePasswordAuthenticationToken(
			adminDetails, "user", adminDetails.getAuthorities());
		context.setAuthentication(adminToken);
		return context;
	}
}
