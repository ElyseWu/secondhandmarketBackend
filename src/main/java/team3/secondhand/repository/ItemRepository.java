package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ItemEntity;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity getItemEntityById(Long itemId);
    List<ItemEntity> getAllByUsername(String username);
    List<ItemEntity> findByCategory(String category);
}
