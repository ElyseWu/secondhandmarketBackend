package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import team3.secondhand.entity.ItemImageEntity;

import java.util.List;

@Repository
public interface ItemImageRepository extends CrudRepository<ItemImageEntity, String> {
    // TODO: we may need to create a method to find all of the images for specific items
    List<ItemImageEntity> getItemImageEntitiesByItemId(Long itemId);

    @Modifying
    @Query("INSERT INTO items_image VALUES(:url, :item_id)")
    void insert(String url, Long item_id);
}
