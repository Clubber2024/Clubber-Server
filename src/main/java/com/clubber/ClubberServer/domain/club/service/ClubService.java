package com.clubber.ClubberServer.domain.club.service;

import com.clubber.ClubberServer.domain.club.domain.Club;
import com.clubber.ClubberServer.domain.club.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClubService {

    private final ClubRepository clubRepository;


    //동아리 및 소모임 정보 저장
    public Long registerClub(Club club){
        if (clubRepository.findById(club.getId()).isPresent()){ //이미 등록된 동아리,소모임인지 확인
           throw new RuntimeException("이미 존재하는 동아리 및 소모임입니다");
        }
        clubRepository.save(club);
        return club.getId();
    }

    //모든 중앙동아리 정보 가져오기
     public List<Club> getCenters(){
        return clubRepository.findByClubType("center");
     }

     //모든 소모임 정보 가져오기
    public List<Club> getSmalls(){
        return clubRepository.findByClubType("small");
    }


    //동아리 및 소모임 개별 페이지 조회
    public Club individualPage(Long clubId){
        Optional<Club> club=clubRepository.findById(clubId);
        if(!club.isPresent()){
            throw new RuntimeException("존재하지 않는 동아리 및 소모임입니다.");
        }
        return club.get();
    }


}
