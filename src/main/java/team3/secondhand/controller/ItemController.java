package team3.secondhand.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.UserEntity;
import team3.secondhand.model.ItemDto;
import team3.secondhand.repository.UserRepository;
import team3.secondhand.service.ItemService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ItemController {
    private final ItemService itemService;
    private final UserRepository userRepository;

    public ItemController(ItemService itemService, UserRepository userRepository) {

        this.itemService = itemService;
        this.userRepository = userRepository;
    }

    @GetMapping("/items")
    public List<ItemDto> getItems(@RequestParam(name = "lat") double lat,
                                  @RequestParam(name = "lon") double lon,
                                  @RequestParam(name = "distance", required=false) String distance) {
        return itemService.getAllItems(lat, lon, distance);
    }

    @GetMapping("/items/my")
    public List<ItemDto> getMyItems(Principal principal) {
        return itemService.getMyItems(principal.getName());
    }

    @GetMapping("/item/{item_id}")
    public ItemDto getItemById(@PathVariable("item_id") Long itemId) {
        return itemService.getItem(itemId);
    }

    @PostMapping("/item")
    public void uploadItem(
            Principal principal,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("description") String description,
            @RequestParam("condition") String condition,
            @RequestParam("category") String category,
            @RequestParam(name = "lat") double lat,
            @RequestParam(name = "lon") double lon,
//            @RequestParam(name = "lat") String lat,
//            @RequestParam(name = "lon") String lon,
            @RequestParam("images") MultipartFile[] images
    ) {
        // For the filed postedDay:
        //  it is the time when we upload item
        //  here I use LocalDate instead of Date
        //  pay attention to change in ItemEntity corresponding field

        // For the filed onSale
        // In the initial state, when we upload an item
        // the item onSale state must be false
        ItemEntity item = new ItemEntity(null, principal.getName(), name, Double.valueOf(price), description, condition, LocalDate.now(), category, false);
        itemService.upload(item, images, lat, lon);
    }

    @DeleteMapping("/item/{item_id}")
    public void deleteItem(@PathVariable("item_id") Long itemId, Principal principal) {

        itemService.deleteItem(itemId, principal.getName());
    }

    @PutMapping("/item/{item_id}")
    public void modifyItem(
            Principal principal,
            @PathVariable("item_id") Long itemId,
            @RequestParam("name") String name,
            @RequestParam("price") String price,
            @RequestParam("description") String description,
            @RequestParam("condition") String condition,
            @RequestParam("category") String category,
            @RequestParam(value = "images", required = false) MultipartFile[] images) {
        itemService.modifyItem(itemId, principal.getName(), name, Double.valueOf(price), description, condition, category, images);
    }

    //mark this item sold or relist
    @PutMapping("/item/{item_id}/soldOrRelist")
    public void markedAsDelete(@PathVariable("item_id") Long itemId) {
        itemService.markItemSoldOrRelist(itemId);
    }
}
