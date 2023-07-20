package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ItemEntity;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity getItemEntityById(Long itemId);
}
