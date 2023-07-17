package team3.secondhand.repository;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;

public interface ItemImageRepository extends CrudRepository<ItemImageEntity, String> {
    // TODO: we may need to create a method to find all of the images for specific items

    @Modifying
    @Query("INSERT INTO items_image VALUES(:url, :item_id)")
    void insert(String url, Long item_id);
}
