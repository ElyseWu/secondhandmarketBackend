package team3.secondhand.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.model.ItemDto;
import team3.secondhand.service.ItemService;

import java.time.LocalDate;

@RestController
public class ItemController {
    private final ItemService itemService;


    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping("/item/{itemId}")
    public ItemDto getItemById(@PathVariable("itemId") Long itemId){
        return itemService.getItem(itemId);
    }

    @PostMapping("/items")
    public void uploadItem(
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("description") String description,
            @RequestParam("condition") String condition,
            @RequestParam("category") String category,
            @RequestParam("images") MultipartFile[] images
    ) {

        // For the filed postedDay:
        //  it is the time when we upload item
        //  here I use LocalDate instead of Date
        //  pay attention to change in ItemEntity corresponding field

        // For the filed onSale
        // In the initial state, when we upload an item
        // the item onSale state must be false

        ItemEntity item = new ItemEntity(name, Double.valueOf(price), description,condition, LocalDate.now(), category, false);
        itemService.upload(item, images);
    }
}
