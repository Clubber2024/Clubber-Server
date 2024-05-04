package com.clubber.ClubberServer.domain.favorite.controller;


import com.clubber.ClubberServer.domain.favorite.dto.FavoriteResponse;
import com.clubber.ClubberServer.domain.favorite.service.FavoriteService;
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

    @PostMapping
    public FavoriteResponse createFavorite(@PathVariable Long clubId){
        return favoriteService.createFavorite(clubId);
    }

    @DeleteMapping("/{favoriteId}")
    public FavoriteResponse deleteFavorite(@PathVariable Long clubId, @PathVariable Long favoriteId){
        return favoriteService.deleteFavorite(clubId, favoriteId);
    }
}