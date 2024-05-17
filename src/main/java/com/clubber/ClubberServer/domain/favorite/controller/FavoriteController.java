package com.clubber.ClubberServer.domain.favorite.controller;


import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/clubs/{clubId}/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @Operation(summary = "동아리 즐겨찾기 추가")
    @PostMapping
    public FavoriteResponse createFavorite(@PathVariable Long clubId){
        return favoriteService.createFavorite(clubId);
    }

    @Operation(summary = "동아리 즐겨찾기 삭제")
    @DeleteMapping("/{favoriteId}")
    public FavoriteResponse deleteFavorite(@PathVariable Long clubId, @PathVariable Long favoriteId){
        return favoriteService.deleteFavorite(clubId, favoriteId);
    }
}
