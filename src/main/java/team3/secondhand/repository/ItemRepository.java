package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import team3.secondhand.entity.ItemEntity;

public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity getItemEntityById(Long itemId);
}
