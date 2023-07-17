package team3.secondhand.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;
import team3.secondhand.repository.ItemImageRepository;
import team3.secondhand.repository.ItemRepository;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    private final ItemImageStorageService itemImageStorageService;

    public ItemService(ItemRepository itemRepository, ItemImageRepository itemImageRepository, ItemImageStorageService itemImageStorageService) {
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
        this.itemImageStorageService = itemImageStorageService;
    }

    // FAKE upload, not finish image uploading and store part
    // TODO: add GCS features & when calling this API, we should also use ItemImageStorageService to store image in CCS
    // TODO: ItemImageStorageService will store images provided by clients in GCS and generate a list of URLs for these images
    // TODO: we can use generated URLs to store in corresponding database table
    @Transactional
    public void upload(ItemEntity item, MultipartFile[] images) {
        // update item repository
        itemRepository.save(item);
        // update item image repository
        // TODO: when we use ItemImageStorage service generate a list of URLs
        // TODO: we can use for loop to generate it to item_image table
        List<String> mediaLinks = Arrays.stream(images).parallel().map(image -> itemImageStorageService.save(image)).collect(Collectors.toList());
        for (String mediaLink : mediaLinks) {
            itemImageRepository.insert(mediaLink, item.getId());
        }


        // WEIRD BUG
        // ItemImageEntity itemImageEntity = new ItemImageEntity("https://fakeurl.com", item.getId());
        // ItemImageRepository.save(itemImageEntity);

        // FAKE image
        //String url = "https://fakeurl.com";
        //itemImageRepository.insert(url, item.getId());
    }
}
