package team3.secondhand.service;

import org.springframework.stereotype.Service;
import team3.secondhand.repository.ItemRepository;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }
}
