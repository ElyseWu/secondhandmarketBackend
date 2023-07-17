package team3.secondhand.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;
import team3.secondhand.repository.ItemImageRepository;
import team3.secondhand.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final ItemImageRepository itemImageRepository;

    public ItemService(ItemRepository itemRepository, ItemImageRepository itemImageRepository) {
        this.itemRepository = itemRepository;
        this.itemImageRepository = itemImageRepository;
    }

    // FAKE upload, not finish image uploading and store part
    // TODO: add GCS features & when calling this API, we should also use ItemImageStorageService to store image in CCS
    // TODO: ItemImageStorageService will store images provided by clients in GCS and generate a list of URLs for these images
    // TODO: we can use generated URLs to store in corresponding database table
    @Transactional
    public void upload(ItemEntity item) {
        // update item repository
        itemRepository.save(item);
        // update item image repository
        // FAKE itemImageEntity
        // TODO: when we use ItemImageStorage service generate a list of URLs
        // TODO: we can use for loop to generate itemImageEntity
        // TODO: each time generation, add it to item_image table

        // WEIRD BUG
        //ItemImageEntity itemImageEntity = new ItemImageEntity("https://fakeurl.com", item.getId());
        //ItemImageRepository.save(itemImageEntity);

        String url = "https://fakeurl.com";
        itemImageRepository.insert(url, item.getId());
    }
}
