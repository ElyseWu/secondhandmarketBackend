package team3.secondhand.controller;

import org.springframework.http.HttpStatus;
import team3.secondhand.service.ItemService;
import org.springframework.web.bind.annotation.*;
import team3.secondhand.model.ItemBody;

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

    @PutMapping("/modify/{itemId}")
    public void modifyItem(
            @PathVariable Long itemId,
            @RequestBody ItemBody body) {
        itemService.modifyItem(itemId, body);
    }
}
