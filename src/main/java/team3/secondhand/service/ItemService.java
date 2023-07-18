package team3.secondhand.service;

import org.springframework.stereotype.Service;
import team3.secondhand.repository.ItemRepository;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.model.ItemBody;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    public void deleteItem(Long itemId) {
        itemRepository.deleteById(itemId);
    }

    public void modifyItem(Long itemId, ItemBody body) {
        LocalDate localDate = LocalDate.parse(body.postedDay(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        // update posted date?
        // localDate = LocalDate.now();
        Date postedDate = Date.valueOf(localDate);
        ItemEntity item = new ItemEntity(
                itemId,
                body.name(),
                body.price(),
                body.description(),
                body.condition(),
                postedDate,
                body.category(),
                body.onSale());
        itemRepository.save(item);
    }
}
