package com.clubber.domain.admin.service;

import com.clubber.domain.admin.domain.Admin;
import com.clubber.domain.admin.dto.UpdateClubPageRequest;
import com.clubber.domain.admin.dto.UpdateClubPageResponse;
import com.clubber.domain.admin.implement.AdminReader;
import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.domain.ClubInfo;
import com.clubber.domain.club.dto.GetClubInfoResponse;
import com.clubber.domain.club.dto.GetClubResponse;
import com.clubber.global.util.ImageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AdminClubService {

	private final AdminReader adminReader;

	@Transactional(readOnly = true)
	public GetClubResponse getAdminsMyPage() {
		Admin admin = adminReader.getCurrentAdmin();
		Club club = admin.getClub();
		return GetClubResponse.of(club, GetClubInfoResponse.from(club.getClubInfo()));
	}

	public UpdateClubPageResponse updateAdminsPage(UpdateClubPageRequest updateClubPageRequest) {
		Admin admin = adminReader.getCurrentAdmin();
		Club club = admin.getClub();

		String imageKey = ImageUtil.parseImageKey(updateClubPageRequest.getImageKey());
		club.updateClub(imageKey, updateClubPageRequest.getIntroduction());

		ClubInfo clubinfo = club.getClubInfo();
		clubinfo.updateClubInfo(updateClubPageRequest.getInstagram(),
			updateClubPageRequest.getYoutube(),
			updateClubPageRequest.getLeader(), updateClubPageRequest.getActivity(),
			updateClubPageRequest.getRoom());

		return UpdateClubPageResponse.of(club, clubinfo);
	}
}
