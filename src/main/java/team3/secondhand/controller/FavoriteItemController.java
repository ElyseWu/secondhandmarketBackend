package team3.secondhand.controller;

import org.springframework.web.bind.annotation.*;
import team3.secondhand.model.ItemDto;
import team3.secondhand.service.FavoriteItemService;

import java.security.Principal;
import java.util.List;

@RestController
public class FavoriteItemController {
    private final FavoriteItemService favoriteItemService;

    public FavoriteItemController(FavoriteItemService favoriteItemService) {
        this.favoriteItemService = favoriteItemService;
    }

    @GetMapping("/my_favorites")
    public List<ItemDto> getMyFavorites(Principal principal) {
        return favoriteItemService.getMyFavoriteItems(principal.getName());
    }

    @PostMapping("/favorite/{item_id}")
    public void markFavorite(@PathVariable("item_id") Long itemId, Principal principal) {
        favoriteItemService.markAsFavorite(itemId, principal.getName());
    }


    @DeleteMapping("/favorite/{item_id}")
    public void markUnFavorite(@PathVariable("item_id") Long itemId, Principal principal) {
        favoriteItemService.unmarkFavorite(principal.getName(), itemId);
    }



}
