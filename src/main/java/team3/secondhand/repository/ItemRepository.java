package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ItemEntity;

import java.util.List;

@Repository
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {
    ItemEntity getItemEntityById(Long itemId);
    ItemEntity getItemEntityByIdAndIsSold(Long itemId, Boolean isSold);
    List<ItemEntity> getAllByUsername(String username);
    List<ItemEntity> findByCategory(String category);
    List<ItemEntity> findAllByCategoryAndIsSold(String category, Boolean isSold);
    List<ItemEntity> findAllByIsSold(Boolean isSold);
}
