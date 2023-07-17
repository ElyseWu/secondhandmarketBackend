package team3.secondhand.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.service.ItemService;

@RestController
public class ItemController {
    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @DeleteMapping("/delete/{itemId}")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public void deleteItem(@PathVariable Long itemId) {
        itemService.deleteItem(itemId);
    }

    @GetMapping("/item/{itemId}")
    public ItemEntity getItemById(@PathVariable("itemId") Long itemId){
        return itemService.getItemById(itemId);
    }

}
