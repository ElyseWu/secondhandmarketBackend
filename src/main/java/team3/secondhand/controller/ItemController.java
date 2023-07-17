package team3.secondhand.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
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

}
