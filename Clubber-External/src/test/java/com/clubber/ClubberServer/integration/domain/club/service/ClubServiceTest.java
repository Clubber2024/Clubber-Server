package com.clubber.ClubberServer.integration.domain.club.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.clubber.domain.domains.club.domain.Club;
import com.clubber.domain.domains.club.domain.ClubType;
import com.clubber.domain.domains.club.domain.Division;
import com.clubber.ClubberServer.domain.club.dto.GetSummaryClubGroupResponse;
import com.clubber.ClubberServer.domain.club.dto.GetSummaryClubResponse;
import com.clubber.domain.domains.club.repository.ClubRepository;
import com.clubber.ClubberServer.domain.club.service.ClubService;
import com.clubber.ClubberServer.integration.util.ServiceTest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ClubServiceTest extends ServiceTest {

    @Autowired
    private ClubRepository clubRepository;

    @Autowired
    private ClubService clubService;

    @Nested
    @DisplayName("한눈에 보기 테스트")
    class SummaryTest {

        @Test
        @DisplayName("중앙동아리를 하나도 빠짐없이 가져오는가")
        void getClubSummary() {

            List<Club> centerClubs = clubRepository.findByClubTypeAndIsDeletedFalse(
                ClubType.CENTER);

            List<GetSummaryClubGroupResponse> clubSummary = clubService.getSummaryClubs();

            List<GetSummaryClubResponse> allClubs = clubSummary.stream()
                .flatMap(response -> response.getClubs().stream())
                .collect(Collectors.toList());

            assertThat(allClubs)
                .hasSameSizeAs(centerClubs) // 실제 DB에서 가져온 동아리들과 개수가 동일한지
                .extracting(GetSummaryClubResponse::getClubId) //실제 DB에서 가져온 동아리들과 clubId가 모두 동일한지
                .containsExactlyInAnyOrderElementsOf(
                    centerClubs.stream().map(Club::getId).collect(Collectors.toList())
                );
        }

        @Test
        @DisplayName("Division enum 클래스에 정의된 분과 순대로 가져오는가")
        void getClubSummaryInRightDivisionOrder() {

            List<GetSummaryClubGroupResponse> clubSummary = clubService.getSummaryClubs();

            List<String> actualDivisions = clubSummary.stream()
                .map(GetSummaryClubGroupResponse::getDivision)
                .collect(Collectors.toList());

            List<String> expectedDivisions = Arrays.stream(Division.values())
                .map(Division::getTitle)
                .collect(Collectors.toList());

            assertThat(expectedDivisions).containsSubsequence(actualDivisions);


        }

    }

}
