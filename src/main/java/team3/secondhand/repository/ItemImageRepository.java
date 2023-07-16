package team3.secondhand.repository;

import org.springframework.data.repository.CrudRepository;
import team3.secondhand.entity.ItemEntity;
import team3.secondhand.entity.ItemImageEntity;

public interface ItemImageRepository extends CrudRepository<ItemImageEntity, String> {
    // TODO: we may need to create a method to find all of the images for specific items
}
